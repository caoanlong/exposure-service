package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 系统角色权限
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRolePermission extends Base {
    private Integer roleId;
    private Integer permissionId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
