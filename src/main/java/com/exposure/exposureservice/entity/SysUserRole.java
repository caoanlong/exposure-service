package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 系统用户角色
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUserRole extends Base {
    private Long userId;
    private Integer roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
