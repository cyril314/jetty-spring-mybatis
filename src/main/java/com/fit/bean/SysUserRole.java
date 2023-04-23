package com.fit.bean;

import com.fit.base.BaseEntity;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysUserRole extends BaseEntity<SysUserRole> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 角色ID (无默认值) */
    private Long roleId;

    /** 用户ID (无默认值) */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}