package com.exposure.exposureservice.controller.interceptors;

import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class AppInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String token = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(token)) {
            JwtUtils jwtUtils = new JwtUtils();
            Claims claims = jwtUtils.parseJWT(token);
            String subject = claims.getSubject();
            request.setAttribute("memberId", subject);
        } else {
            throw new CommonException(ErrorCode.UNLOGIN_ERROR);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        String memberId = (String) request.getAttribute("memberId");
        String key = "isOnline:" + memberId;
        stringRedisTemplate.opsForValue().set(key, "1");
        stringRedisTemplate.expire(key, 2, TimeUnit.MINUTES);
    }

}
