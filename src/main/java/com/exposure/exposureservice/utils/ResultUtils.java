package com.exposure.exposureservice.utils;

import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.enums.ErrorCode;

public class ResultUtils {
    public static ResultBean<Object> success(Object object) {
        ResultBean<Object> result = new ResultBean<Object>();
        result.setCode(200);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ResultBean<Object> success() {
        return success(null);
    }

    public static ResultBean<Object> error(Integer code, String msg) {
        ResultBean<Object> result = new ResultBean<Object>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResultBean<Object> error(ErrorCode errorCode) {
        ResultBean<Object> result = new ResultBean<Object>();
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        return result;
    }
}
