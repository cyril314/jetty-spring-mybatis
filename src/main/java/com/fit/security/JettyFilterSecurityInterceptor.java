package com.fit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * @AUTO 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 *       securityMetadataSource相当于本包中自定义的DefaultFilterSecurityInterceptor。
 *       该方法的作用提从数据库提取权限和资源，装配到HashMap中，供Spring Security使用，用于权限校验
 * @Author AIM
 * @DATE 2017/11/12
 */
public class JettyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	private static Logger logger = LoggerFactory.getLogger(JettyFilterSecurityInterceptor.class);

	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.debug("================= DefaultFilterSecurityInterceptor过滤器 =================");
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		logger.debug("================= DefaultFilterSecurityInterceptor过滤器invoke方法 =================");
		// 会调用我们定义的accessDecisionManager:decide(Object
		// object)和securityMetadataSource:getAttributes(Object object)方法。
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	public void destroy() {
	}

	public void init(FilterConfig filterconfig) throws ServletException {
	}
}
