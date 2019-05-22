package com.exposure.exposureservice.controller.interceptors;

import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("---------------------开始进入请求地址拦截----------------------------");
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,Authorization,X-Requested-With");

        String token = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(token)) {
            JwtUtils jwtUtils = new JwtUtils();
            Claims claims = jwtUtils.parseJWT(token);
            String subject = claims.getSubject();
            request.setAttribute("userId", subject);
        } else {
            throw new CommonException(ErrorCode.UNLOGIN_ERROR);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        System.out.println("---------------视图渲染之后的操作-------------------------");
    }

}
