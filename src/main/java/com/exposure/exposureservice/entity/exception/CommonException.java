package com.exposure.exposureservice.entity.exception;

import com.exposure.exposureservice.enums.ErrorCode;

public class CommonException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}
