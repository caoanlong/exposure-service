package com.exposure.exposureservice.utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
    public static String md5(String text, String salt) {
        String str = DigestUtils.md5DigestAsHex((text + salt).getBytes());
        return str;
    }
}
