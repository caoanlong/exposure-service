package com.exposure.exposureservice.entity;

import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.utils.MD5Utils;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member implements Serializable {
    private Long id;
    private String mobile;
    private String email;
    private String userName;
    private String password;
    private String nickName;
    private String realName;
    private String avatar;
    private Integer isActive;
    private Date createTime;
    private String lastLoginIp;
    private Date lastLoginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        String pwd = MD5Utils.md5(password, Constant.MD5_SALT);
        this.password = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
