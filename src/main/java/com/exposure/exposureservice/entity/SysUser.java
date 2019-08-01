package com.exposure.exposureservice.entity;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.utils.MD5Utils;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 系统用户
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysUser extends Base {
    private Long id;
    private String userName;
    private String password;
    private String salt;
    private String avatar;
    private String mobile;
    private String email;
    private Integer roleId;
    private String roleName;
    private String permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String pwd = MD5Utils.md5(password, Constant.MD5_SALT_SYSUSER);
        this.password = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
