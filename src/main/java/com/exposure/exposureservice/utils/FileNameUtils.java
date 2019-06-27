package com.exposure.exposureservice.utils;

public class FileNameUtils {
    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i == -1) {
            return "." + fileName;
        }
        return fileName.substring(i);
    }

    /**
     * 生成新的文件名
     * @param fileOriginName  源文件名
     * @return
     */
    public static String getFileName(String fileOriginName) {
        return UUIDUtils.getUUID() + FileNameUtils.getSuffix(fileOriginName);
    }
}
