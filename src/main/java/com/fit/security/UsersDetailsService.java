package com.fit.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fit.bean.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * @AUTO 用户验证服务
 * @FILE UsersDetailsService.java
 * @DATE 2017-11-2 下午4:32:58
 * @Author AIM
 */
public class UsersDetailsService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UsersDetailsService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String sql = "SELECT a.`role_name` FROM `sys_role` a,`sys_user_role` b WHERE b.`role_id`=a.`id` AND b.`user_id`=?";
	private final String uql = "SELECT * FROM `sys_user` WHERE `username`=?";

	@Value("${isTest}")
	private final boolean isTest = true;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("=================================> 查询用户的名称：{}", username);
		if (isTest) {
			if (StringUtils.isEmpty(username)) {
				throw new UsernameNotFoundException("用户名不能为空！");// 用户找不到
			}
			SysUser user = jdbcTemplate.queryForObject(uql, new BeanPropertyRowMapper<SysUser>(SysUser.class), username);
			if (!user.getEnabled()) {
				throw new UsernameNotFoundException("该用户处于锁定状态！");// 账户锁定
			}
			List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, user.getId());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for (Map<String, Object> map : mapList) {
				if (map == null) {
					continue;
				} else {
					String roleName = map.get("role_name").toString();
					if (roleName != null) {
						GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
						// 1：此处将权限信息添加到 GrantedAuthority
						// 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
						grantedAuthorities.add(grantedAuthority);
					}
				}
			}

			logger.info("得到其权限：{}", grantedAuthorities);
			return new UserDetail(user, grantedAuthorities);
		} else {
			SysUser user = new SysUser();
			user.setUsername("admin");
			user.setPassword("21232f297a57a5a743894a0e4a801fc3");
			user.setEnabled(true);
			logger.debug("================= 测试使用用户信息：{}", user.toString());
			return new UserDetail(user, getAuthorities("ROLE_ROOT"));
		}
	}

	private Collection<GrantedAuthority> getAuthorities(String role) {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
		grantedAuthorities.add(grantedAuthority);
		logger.debug("getAuthorities 得到其权限：{}", grantedAuthorities);
		return grantedAuthorities;
	}
}
