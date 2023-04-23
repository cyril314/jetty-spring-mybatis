package com.fit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @AUTO 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 *       此类在初始化时，应该取到所有资源及其对应角色的定义。 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色去访问
 * @Author AIM
 * @DATE 2017/11/12
 */
public class JettyInvocationSecurityMetadataSource extends JdbcDaoSupport implements FilterInvocationSecurityMetadataSource {

	private static Logger logger = LoggerFactory.getLogger(JettyInvocationSecurityMetadataSource.class);

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private String resurl = "SELECT o.`resurl` FROM `sys_resources` o,`sys_role_auth` a,`sys_role` r "
			+ " WHERE o.`id`=a.`auth_id` AND a.`role_id`=r.`id` AND r.`role_name`=?";

	// TOMCAT启动时实例化一次
	public JettyInvocationSecurityMetadataSource(DataSource dataSource) {
		this.setDataSource(dataSource);
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		logger.info("----> 第一步, 读取数据库所有的url、角色");
		// 在Web服务器启动时，提取系统中的所有权限。
		String sql = "SELECT r.`role_name` FROM `sys_role` r";
		List<String> query = getJdbcTemplate().query(sql, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		// 应当是资源为key， 权限为value。 资源通常为URL， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		if (query != null) {
			for (String auth : query) {
				ConfigAttribute ca = new SecurityConfig(auth);

				List<String> list = getJdbcTemplate().query(resurl, new Object[] { auth }, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString(1);
					}
				});

				for (String url : list) {
					// 判断资源文件和权限的对应关系，如果已经存在相关的资源URL，则要通过该URL为key提取出权限集合，将权限增加到权限集合中。
					if (resourceMap.containsKey(url)) {
						Collection<ConfigAttribute> value = resourceMap.get(url);
						value.add(ca);
						resourceMap.put(url, value);
					} else {
						Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
						atts.add(ca);
						resourceMap.put(url, atts);
					}
				}
			}
		}
		logger.debug("----> 初始化的权限集合:{}", resourceMap);
	}

	// 根据URL，找到相关的权限配置。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// object 是一个URL，被用户请求的URL。
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String url = filterInvocation.getRequestUrl();
		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		logger.info("-> Request Path：" + url);
		Iterator<String> ite = resourceMap.keySet().iterator();
		HttpServletRequest httpRequest = filterInvocation.getHttpRequest();
		while (ite.hasNext()) {
			String requestURL = ite.next();
			// 完全路径URL方式路径匹配
			RequestMatcher requestMatcher = new AntPathRequestMatcher(requestURL);
			if (requestMatcher.matches(httpRequest)) {
				Collection<ConfigAttribute> retCollection = resourceMap.get(requestURL);
				logger.debug("---> 根据URL，找到相关的权限配置 <----");
				logger.debug("---> 匹配成功返回集合：" + retCollection);
				return retCollection;
			}
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("----> 第二步, 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同,或是否是其超类或超接口");
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		logger.info("----> 第三步, 获取可以访问的权限信息");
		Set<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			attributes.addAll(entry.getValue());
		}
		logger.debug("----> There are all these permissions：" + attributes.toString());
		return attributes;
	}

	/**
	 * 刷新缓存
	 */
	public void refreshAllConfigAttributes() {
		getAllConfigAttributes();
	}
}
