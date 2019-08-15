package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 系统权限
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysPermission extends Base {
    private Integer id;
    private Integer pid;
    private String perName;
    private String perType;
    private String permission;
    private String url;
    private Integer sort;
    private List<SysPermission> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
    }
}
