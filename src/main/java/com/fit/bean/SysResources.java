package com.fit.bean;

import com.fit.base.BaseEntity;

import java.util.Date;

/**
 * @AUTO 
 * @DATE 2021年6月21日14:33:54
 * @Author AIM
 * @Version 2.0
 */
public class SysResources extends BaseEntity<SysResources> {
    private static final long serialVersionUID = 1L;

    /** 主键 (主健ID) (无默认值) */
    private Long id;

    /** 创建时间 (无默认值) */
    private Date creatdate;

    /** 资源名称 (无默认值) */
    private String name;

    /** 资源类型(M:目录 C:菜单 A:按钮) (无默认值) */
    private String type;

    /** 资源图标 (无默认值) */
    private String icon;

    /** 资源优先权 (无默认值) */
    private Integer priority;

    /** 资源链接 (无默认值) */
    private String resurl;

    /** 资源描述 (无默认值) */
    private String description;

    /** 是否被禁用 0禁用1正常  (默认值为: 0) */
    private Boolean enabled;

    /** 是否是超级权限 0非1是  (默认值为: 0) */
    private Integer isys;

    /** 父级ID  (默认值为: 0) */
    private Integer pid;

    /** 父级名称 (无默认值) */
    private String pname;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getResurl() {
        return resurl;
    }

    public void setResurl(String resurl) {
        this.resurl = resurl == null ? null : resurl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }
}