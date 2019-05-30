package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 系统角色
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole extends Base {
    private Integer id;
    private String roleName;
    private String permissions;

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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
