package com.upfly.config;

import javax.servlet.Filter;

import java.util.LinkedHashMap;
import java.util.Map;

import com.upfly.filter.ShiroFilter;
import com.upfly.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 自定义拦截器
        Map<String, Filter> customisedFilter = new LinkedHashMap<>();
        customisedFilter.put("url", new ShiroFilter());

        // 配置拦截链 使用LinkedHashMap 因为LinkedHashMap是有序的 shiro会根据添加的顺序进行拦截
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/guozhenwei", "anon");
        filterChainMap.put("/guozhenwei/login", "anon");
        filterChainMap.put("/guozhenwei/**", "url");

        shiroFilterFactoryBean.setLoginUrl("/guozhenwei");
        shiroFilterFactoryBean.setFilters(customisedFilter);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

}
