package com.fit.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fit.util.WebUtil;

/**
 * @AUTO 自定义访问拒绝处理程序
 * @DATE 2019-7-3 下午2:25:21
 * @Author AIM
 * @version v2.0
 * @Company 天际友盟
 */
public class JettyAccessDeniedHandler implements AccessDeniedHandler {
	// 自定义跳转的路径
	private String accessDeniedUrl;

	public JettyAccessDeniedHandler() {
		super();
	}

	public JettyAccessDeniedHandler(String accessDeniedUrl) {
		super();
		this.accessDeniedUrl = accessDeniedUrl;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		// AJAX请求,使用response发送403
		if (WebUtil.isAjaxRequest(request)) {
			response.sendError(403, accessDeniedException.getMessage());
		} else if (!response.isCommitted()) {// 非AJAX请求，跳转系统默认的403错误界面，在web.xml中配置
			if (accessDeniedUrl != null) {
				// Put exception into request scope (perhaps of use to a view)
				request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

				// Set the 403 status code.
				response.setStatus(HttpStatus.FORBIDDEN.value());

				// forward to error page.
				RequestDispatcher dispatcher = request.getRequestDispatcher(accessDeniedUrl);
				dispatcher.forward(request, response);
			} else {
				response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
			}
		}
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}
}
