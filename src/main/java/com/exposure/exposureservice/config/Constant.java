package com.exposure.exposureservice.config;

import java.util.UUID;

public class Constant {
    public static final String JWT_ID = UUID.randomUUID().toString();
    public static final String JWT_SECRET = "cao123789";
    public static final long JWT_TTL = 86400000 * 30;

    // MD5盐
    public static final String MD5_SALT = "caonimadebi";

    public static final Integer SMSCODE_NUM = 6;
    public static final Integer SMSCODE_EXPIRE = 60*5;
}
