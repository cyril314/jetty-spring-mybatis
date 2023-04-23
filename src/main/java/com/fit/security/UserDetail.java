package com.fit.security;

import java.util.Collection;

import com.fit.bean.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @AUTO 安全用户验证实体
 * @FILE UserDetail.java
 * @DATE 2017-11-2 下午4:32:31
 * @Author AIM
 */
public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;

	private SysUser user;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetail(SysUser user) {
		this.user = user;
	}

	public UserDetail(SysUser user, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public UserDetail(Long id, String username, String password, Boolean enabled,
	                  Collection<? extends GrantedAuthority> authorities) {
		SysUser sysUserBean = new SysUser();
		sysUserBean.setName(username);
		sysUserBean.setId(id);
		sysUserBean.setPassword(password);
		sysUserBean.setEnabled(enabled);
		this.user = sysUserBean;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.getEnabled();
	}

}