package com.fit.bean;

import com.fit.base.BaseEntity;

import java.util.Date;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysUser extends BaseEntity<SysUser> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 创建时间 (无默认值) */
    private Date creatdate;

    /** 用户姓名 (无默认值) */
    private String name;

    /** 登陆用户名(登陆号) (无默认值) */
    private String username;

    /** 用户密码 (无默认值) */
    private String password;

    /** 描述 (无默认值) */
    private String desc;

    /** 是否被禁用 0禁用1正常  (默认值为: 0) */
    private Boolean enabled;

    /** 是否是超级用户 0非1是  (默认值为: 0) */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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