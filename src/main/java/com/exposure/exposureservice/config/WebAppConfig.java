package com.exposure.exposureservice.config;

import com.exposure.exposureservice.controller.interceptors.AppInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    // 将拦截器注册为Bean，交给Spring IOC容器管理，则可以注入其他Bean
    @Bean
    AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(appInterceptor())
                .addPathPatterns("/app/**")
                .excludePathPatterns(
                        "/app/member/login",
                        "/app/member/register",
                        "/app/member/active",
                        "/app/member/getEmailCode",
                        "/app/member/resetPassword",
                        "/app/thing/findList",
                        "/app/common/statistics"
                );
    }
}
