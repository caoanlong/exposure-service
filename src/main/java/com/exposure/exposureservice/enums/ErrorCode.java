package com.exposure.exposureservice.enums;

public enum ErrorCode {
    /**
     * 用户名不能为空
     */
    USERNAME_NOTNULL(100, "用户名不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_NOTNULL(101, "密码不能为空"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(102, "密码错误"),
    /**
     * 用户不存在
     */
    USERNAMEORPASSWORD_ERROR(103, "帐号或密码错误"),
    /**
     * 未登录错误
     */
    UNLOGIN_ERROR(104, "未登录"),
    /**
     * token错误
     */
    TOKEN_ERROR(105, "token错误"),
    /**
     * token过期
     */
    TOKEN_EXPIRE(106, "token过期"),
    /**
     * openid不能为空
     */
    OPENID_NOTNULL(107, "openid不能为空"),

    /**
     * 验证码错误
     */
    SMSCODE_ERROR(108, "验证码错误"),

    /**
     * 用户已存在
     */
    USERNAME_EXIST(109, "用户已存在"),

    /**
     * 角色不能为空
     */
    ROLE_NOTNULL(1000, "角色不能为空"),

    /**
     * 用户ID不能为空
     */
    USERID_NOTNULL(1001, "用户ID不能为空"),

    /**
     * 角色ID不能为空
     */
    ROLEID_NOTNULL(1002, "角色ID不能为空"),

    /**
     * 姓名不能为空
     */
    NAME_NOTNULL(1003, "姓名不能为空"),

    /**
     * 手机不能为空
     */
    MOBILE_NOTNULL(1004, "手机不能为空"),

    /**
     * 手机号已存在
     */
    MOBILE_EXIST(1009, "手机号已存在"),

    /**
     * 手机号格式不正确
     */
    MOBILE_FORMAT_ERROR(1016, "手机号格式不正确"),

    /**
     * 短信验证码不能为空
     */
    SMSCODE_NOTNULL(1017, "短信验证码不能为空"),

    /**
     * 标题不能为空
     */
    TITLE_NOTNULL(1018, "标题不能为空"),

    /**
     * 详情不能为空
     */
    INFO_NOTNULL(1019, "详情不能为空"),

    /**
     * ID不能为空
     */
    ID_NOTNULL(1020, "ID不能为空"),

    /**
     * type错误
     */
    TYPE_ERROR(1021, "type错误"),

    /**
     * type不能为空
     */
    TYPE_NOTNULL(1022, "type不能为空"),

    /**
     * 图片不能为空
     */
    IMG_NOTNULL(2000, "图片不能为空"),

    /**
     * 图片格式错误
     */
    IMG_FORMAT_ERROR(2001, "图片格式错误"),

    /**
     * 图片过大
     */
    IMG_TOO_LARGE(2002, "图片过大"),

    /**
     * 图片上传失败
     */
    IMG_UPLOAD_ERROR(2003, "图片上传失败"),

    /**
     * 未知错误
     */
    UNKONW_ERROR(-1, "未知错误");

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
