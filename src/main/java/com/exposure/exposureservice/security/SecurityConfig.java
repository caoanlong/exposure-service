package com.exposure.exposureservice.security;

import com.alibaba.fastjson.JSON;
import com.exposure.exposureservice.entity.ResultBean;
import com.exposure.exposureservice.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SysUserDetailService sysUserDetailService() {
        return new SysUserDetailService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println(http);
        http.csrf().disable()  // 禁用 Spring Security 自带的跨域处理
                .authorizeRequests()
                // 开启swagger-ui权限
                .antMatchers(
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/app/**"
                ).permitAll()
                .antMatchers("/admin/sysUser/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated().and()  // 剩下所有的验证都需要验证
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  // 定制我们自己的 session 策略：调整为让 Spring Security 不创建和使用 session
                .formLogin()
                    .disable() //禁用security的表单登录，自定义实现
                .logout()
                    .logoutSuccessHandler(new BaseAuthHandler()).and()
                .exceptionHandling()
                    .accessDeniedHandler(new BaseAuthHandler())
                    .authenticationEntryPoint(new BaseAuthHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserDetailService());
    }

    private static class BaseAuthHandler implements AccessDeniedHandler, AuthenticationEntryPoint, LogoutSuccessHandler {

        private void unAuth(HttpServletResponse response) throws IOException {
            ResultBean<Object> result = new ResultBean<>();
            result.setCode(ErrorCode.NO_PERMISSION.getCode());
            result.setMsg(ErrorCode.NO_PERMISSION.getMsg());
            String json = JSON.toJSONString(result);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(json);
        }

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
            unAuth(response);
        }

        // 未登录时访问
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            System.out.println(e);
            unAuth(response);
        }

        // 退出成功
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            ResultBean<Object> result = new ResultBean<>();
            result.setCode(ErrorCode.LOGOUT_SUCCESS.getCode());
            result.setMsg(ErrorCode.LOGOUT_SUCCESS.getMsg());
            String json = JSON.toJSONString(result);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }
}
