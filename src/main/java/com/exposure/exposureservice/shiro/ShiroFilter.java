package com.exposure.exposureservice.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShiroFilter extends BasicHttpAuthenticationFilter {

    // 判断Token头是否为空
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader("Authorization") != null;
    }

    /**
     * 首先调用的一个方法，在该方法内进行主要的认证逻辑处理，如判断Token头是否为空，解密Token等
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            boolean result = executeLogin(request, response);  // 执行登录
            if (!result) { // 登录失败
                return tokenError(request, response);
            }
            return true;
        }
        // Token为空
        return tokenNull(request, response);
    }

    /**
     * 调用Realm执行登录，并返回登录认证的结果
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        JwtToken token = new JwtToken(authorization);
        // 当调用Subject对象的login方法时，将会交给我们自己实现的MyRealm来处理登录的认证逻辑
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            request.setAttribute("sysUserId", subject.getPrincipal());
        } catch (Exception e) {
            return false; // 登录失败
        }
        return true;  // 登录成功
    }

    private boolean tokenNull(ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/common/tokenNull");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean tokenError(ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/common/tokenError");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 返回HTTP 401错误
     */
    private boolean abort401(ServletRequest request, ServletResponse response) {
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/common/401");
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
