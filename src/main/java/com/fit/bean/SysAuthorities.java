package com.fit.bean;

import com.fit.base.BaseEntity;

import java.util.Date;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysAuthorities extends BaseEntity<SysAuthorities> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 创建时间 (无默认值) */
    private Date creatdate;

    /** 权限名称 (无默认值) */
    private String name;

    /** 权限描述 (无默认值) */
    private String desc;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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