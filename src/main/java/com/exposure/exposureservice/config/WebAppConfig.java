package com.exposure.exposureservice.config;

import com.exposure.exposureservice.controller.interceptors.AdminInterceptor;
import com.exposure.exposureservice.controller.interceptors.AppInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin/sysUser/login"
                );
        registry.addInterceptor(new AppInterceptor())
                .addPathPatterns("/app/**")
                .excludePathPatterns(
                        "/app/member/login",
                        "/app/member/register",
                        "/app/member/active",
                        "/app/member/getEmailCode",
                        "/app/member/resetPassword",
                        "/app/thing/findList"
                );
    }
}
