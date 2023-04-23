package com.fit.bean;

import com.fit.base.BaseEntity;

import java.util.Date;

/**
 * @AUTO 角色
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysRole extends BaseEntity<SysRole> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 创建时间 (无默认值) */
    private Date creatdate;

    /** 角色名字 (无默认值) */
    private String roleName;

    /** 角色说明 (无默认值) */
    private String roleDesc;

    /** 是否被禁用 0禁用1正常  (默认值为: 0) */
    private Boolean enabled;

    /** 是否是超级权限 0非1是  (默认值为: 0) */
    private Integer isys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getIsys() {
        return isys;
    }

    public void setIsys(Integer isys) {
        this.isys = isys;
    }
}