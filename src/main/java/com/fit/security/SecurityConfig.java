package com.fit.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @AUTO 权限配置文件
 * @FILE SecurityConfig.java
 * @DATE 2021年6月4日 下午4:51:19
 * @Author AIM
 * @Version
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DruidDataSource dataSource;

	/**
	 * 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源
	 */
	@Bean
	public JettyAccessDecisionManager jettyAccessDecisionManager() {
		return new JettyAccessDecisionManager();
	}

	/**
	 * 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色去访问
	 */
	@Bean
	public JettyInvocationSecurityMetadataSource jettySecurityMetadataSource() {
		return new JettyInvocationSecurityMetadataSource(dataSource);
	}

	/**
	 * 密码加密
	 */
	@Bean
	public JettyPasswordEncoder passwordEncoder() {
		return new JettyPasswordEncoder();
	}

	/**
	 * 用户详细信息服务
	 */
	@Bean
	public UsersDetailsService usersDetailsService() {
		return new UsersDetailsService();
	}

	/**
	 * 自定义身份验证提供者
	 */
	@Bean
	public AuthenticationProvider customAuthenticationProvider() {
		JettyDaoAuthenticationProvider provider = new JettyDaoAuthenticationProvider();
		provider.setHideUserNotFoundExceptions(false);
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(usersDetailsService());
		return provider;
	}

	/**
	 * 自定义安全过滤拦截器
	 */
	@Bean
	public JettyFilterSecurityInterceptor customFilterSecurityInterceptor() {
		JettyFilterSecurityInterceptor interceptor = new JettyFilterSecurityInterceptor();
		// 用户拥有的权限
		interceptor.setAccessDecisionManager(jettyAccessDecisionManager());
		// 用户是否拥有所请求资源的权限
		ProviderManager providerManager = new ProviderManager(Collections.singletonList(customAuthenticationProvider()));
		interceptor.setAuthenticationManager(providerManager);
		// 资源与权限对应关系
		interceptor.setSecurityMetadataSource(jettySecurityMetadataSource());
		return interceptor;
	}

	/**
	 * 自定义请求防火墙
	 */
	@Bean
	public HttpFirewall customHttpFirewall() {
		return new JettyHttpFirewall();
	}

	/**
	 * 安全过滤链接代理
	 */
	@Bean
	public FilterChainProxy securityFilterChainProxy() {
		List<SecurityFilterChain> filterChains = new ArrayList<SecurityFilterChain>();
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/favicon.ico")));
		filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/static/**")));
		return new FilterChainProxy(filterChains);
	}

	private String[] loadExcludePath() {
		return new String[] { "/", "/login", "/logout", "/favicon.ico", "/static/**", "/error/**", "/img/**", "/js/**", "/css/**", "/lib/**" };
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(loadExcludePath());
	}

	/**
	 * 自定义访问拒绝处理程序
	 */
	@Bean
	public JettyAccessDeniedHandler jettyAccessDeniedHandler() {
		return new JettyAccessDeniedHandler("/admin/denied");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * .disable(); // 任何跨域
		 */
		http.headers().frameOptions().sameOrigin(); // 同源跨域
		/*
		 * 关闭跨域限制
		 */
		http.csrf().disable().authorizeRequests() // 开启登录配置
				.anyRequest().authenticated() // 表示剩余的其他接口，登录之后就能访问
				// 登录路径及登录处理路径
				.and().formLogin().loginPage("/login").loginProcessingUrl("/checkLogin").failureUrl("/login?msg=fault")
				// 用户密码字段
				.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/admin", true).permitAll()
				// 登出路径及跳转登录页
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID").permitAll();

		// 自定义权限拦截器
		http.addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
		// 自定义访问拒绝处理程序
		http.exceptionHandling().accessDeniedHandler(jettyAccessDeniedHandler());
	}
}
