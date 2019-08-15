package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole extends Base {
    private Integer id;
    private String roleName;
    private List<Integer> permission = new ArrayList<>();
    private List<SysPermission> sysPermissions = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getPermission() {
        return permission;
    }

    public void setPermission(List<Integer> permission) {
        this.permission = permission;
    }

    public List<SysPermission> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(List<SysPermission> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }
}
