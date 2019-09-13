package com.exposure.exposureservice.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.exposure.exposureservice.config.Constant;
import com.exposure.exposureservice.utils.MD5Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public MyUsernamePasswordAuthenticationFilter() {
        // 拦截url为 "/admin/sysUser/login" 的POST请求
        super(new AntPathRequestMatcher("/admin/sysUser/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String userName = null, password = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObject = JSON.parseObject(body);
            userName = jsonObject.getString("userName");
            password = jsonObject.getString("password");
        }
        if (userName == null) {
            userName = "";
        }
        if (userName == null) {
            password = "";
        }
        userName = userName.trim();
        password = MD5Utils.md5(password, Constant.MD5_SALT_SYSUSER);
        // 封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
        System.out.println(authenticate);
        return authenticate;
    }
}
