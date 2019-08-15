package com.exposure.exposureservice.config;


import com.exposure.exposureservice.shiro.ShiroFilter;
import com.exposure.exposureservice.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        return redisManager;
    }

    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 安全管理器
     * 生成DefaultWebSecurityManager bean，并设置我们自定义的Realm
     * userRealm Spring将会自动注入UserRealm类，因为我们给它加了@Component注解
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错，直接引用shiro-spring则不报错
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 设置过滤规则
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 添加ShiroFilter过滤器且命名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/common/tokenNull");  // 设置认证失败的路径

        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 排除401路径，ShiroFilter将不做过滤的操作
        filterRuleMap.put("/admin/sysUser/login", "anon");
        filterRuleMap.put("/common/tokenNull", "anon");
        filterRuleMap.put("/common/tokenError", "anon");
//        filterRuleMap.put("/common/upload", "anon");
        filterRuleMap.put("/app/**", "anon");
        // 所有的请求通过ShiroFilter执行处理
        filterRuleMap.put("/admin/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilterFactoryBean;
    }
}
