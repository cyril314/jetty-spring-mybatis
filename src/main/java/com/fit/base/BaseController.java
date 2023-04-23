package com.fit.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fit.util.ConverterUtils;
import com.fit.util.FastJsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @AUTO 控制层基类
 * @FILE BaseController.java
 * @DATE 2018-3-23 下午2:38:18
 * @Author AIM
 */
public class BaseController {

	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected String getJsonObject(Object obj) {
		return FastJsonUtils.getToJson(obj);
	}

	/**
	 * 将数据保存到session
	 *
	 * @param key
	 *            存入session键名
	 * @param value
	 *            存入session值
	 */
	protected void setValue2Session(HttpServletRequest res, String key, Object value) {
		res.getSession().setAttribute(key, value);
	}

	/**
	 * 从session中获取数据
	 *
	 * @param key
	 *            取出session键名
	 */
	protected Object getValueFromSession(HttpServletRequest res, String key) {
		return res.getSession().getAttribute(key);
	}

	/**
	 * 将数据从session中删除
	 *
	 * @param key
	 *            删除session键名
	 */
	protected void removeValueFromSession(HttpServletRequest res, String key) {
		res.getSession().removeAttribute(key);
	}

	/**
	 * 将JSON数据写到客户端
	 */
	protected void writeJson(HttpServletResponse response, String json) {
		try {
			logger.debug("服务器返回信息：==>" + json);
			this.getPrintWriter(response).write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将数据以JSON格式写到客户端
	 */
	protected void writeObjectAsJson(HttpServletResponse response, Object obj) {
		try {
			logger.debug("服务器返回信息：==>" + this.getJsonObject(obj));
			this.getPrintWriter(response).write(this.getJsonObject(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取书写器
	 */
	protected PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		return response.getWriter();
	}

	/**
	 * 获取请求的所有参数
	 */
	protected Map<String, Object> getRequestParamsMap(HttpServletRequest request) {
		// 返回值Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			// 参数Map
			Map<String, String[]> properties = request.getParameterMap();
			String value = "";
			for (Iterator<?> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
				Map.Entry<?, ?> element = (Map.Entry<?, ?>) iter.next();
				Object strKey = element.getKey();
				Object strObj = element.getValue();
				if (null == strObj) {
					value = "";
				} else if (strObj instanceof String[]) {
					String[] values = (String[]) strObj;
					for (int i = 0; i < values.length; i++) { // 用于请求参数中有多个相同名称
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = strObj.toString();// 用于请求参数中请求参数名唯一
				}
				returnMap.put(strKey.toString(), value);
			}
		} catch (Exception e) {
			logger.error("getRequestParamsMap错误信息：{}", e);
		}
		return returnMap;
	}

	protected Map<String, Object> basePageParam(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> params = getRequestParamsMap(request);
		Integer pageNum = ConverterUtils.toInt(request.getParameter("pageNum"), 1);
		Integer pageSize = ConverterUtils.toInt(request.getParameter("pageSize"), 10);
		params.put("offset", (pageNum - 1) * pageSize);
		params.put("limit", pageSize);
		params.remove("pageNum");
		params.remove("pageSize");

		return params;
	}
}
