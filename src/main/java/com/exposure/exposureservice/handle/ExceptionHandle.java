package com.exposure.exposureservice.handle;

import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.utils.ResultUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionHandle {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean handle(Exception e) {
        logger.error(e.getMessage());
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            return ResultUtils.error(commonException.getCode(), commonException.getMessage());
        } else if (e instanceof MissingServletRequestParameterException) {
            // 参数缺失错误
            return ResultUtils.error(400, e.getMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            // 请求方式错误
            return ResultUtils.error(405, e.getMessage());
        } else if (e instanceof ExpiredJwtException){
            // token过期
            return ResultUtils.error(ErrorCode.TOKEN_EXPIRE);
        } else if (e instanceof MalformedJwtException) {
            // token错误
            return ResultUtils.error(ErrorCode.TOKEN_ERROR);
        } else if (e instanceof MaxUploadSizeExceededException) {
            // 图片过大
            return ResultUtils.error(ErrorCode.IMG_TOO_LARGE);
        } else {
            // 未知错误
            return ResultUtils.error(ErrorCode.UNKONW_ERROR);
        }
    }
}
