package com.fit.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @AUTO 封装的JSON转换工具类
 * @DATE 2019-4-8 下午3:42:04
 * @Author AIM
 * @version v2.0
 * @Company 天际友盟
 */
public class FastJsonUtils {

	/**
	 * 功能描述：把JSON数据转换成指定的java对象
	 * 
	 * @param jsonData
	 *            JSON数据
	 * @param clazz
	 *            指定的java对象
	 */
	public static <T> T getJsonToBean(String jsonData, Class<T> clazz) {
		return JSON.parseObject(jsonData, clazz);
	}

	/**
	 * 功能描述：把java对象转换成JSON数据
	 * 
	 * @param object
	 *            java对象
	 */
	public static String getToJson(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 功能描述：把JSON数据转换成指定的java对象列表
	 * 
	 * @param jsonData
	 *            JSON数据
	 * @param clazz
	 *            指定的java对象
	 */
	public static <T> List<T> getJsonToList(String jsonData, Class<T> clazz) {
		return JSON.parseArray(jsonData, clazz);
	}

	/**
	 * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
	 * 
	 * @param jsonData
	 *            JSON数据
	 */
	public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
		return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
		});
	}

	/**
	 * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
	 * 
	 * @param jsonData
	 *            JSON数据
	 */
	public static Map<String, Object> getJsonToMap(String jsonData) {
		return JSON.parseObject(jsonData, new TypeReference<Map<String, Object>>() {
		});
	}
}
