package com.exposure.exposureservice.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
