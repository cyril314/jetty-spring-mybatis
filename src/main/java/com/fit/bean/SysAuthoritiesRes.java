package com.fit.bean;

import com.fit.base.BaseEntity;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysAuthoritiesRes extends BaseEntity<SysAuthoritiesRes> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 权限id (无默认值) */
    private Long authId;

    /** 资源id (无默认值) */
    private Long resId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }
}