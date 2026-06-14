package com.drone.management.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Apache Shiro 安全配置 — 完全手动配置
 *
 * shiro-spring-boot-web-starter 自动配置已在启动类中排除，
 * 所有 Shiro Bean 由此类手动创建，避免 Bean 定义冲突。
 */
@Configuration
public class ShiroConfig {

    /**
     * 自定义 Realm
     */
    @Bean
    public DroneRealm droneRealm() {
        return new DroneRealm();
    }

    /**
     * Authenticator — 认证器
     */
    @Bean
    public Authenticator authenticator(Realm realm) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Collections.singletonList(realm));
        return authenticator;
    }

    /**
     * Authorizer — 授权器
     */
    @Bean
    public Authorizer authorizer(Realm realm) {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(Collections.singletonList(realm));
        return authorizer;
    }

    /**
     * SecurityManager — 安全管理器
     */
    @Bean
    public SecurityManager securityManager(Realm realm,
                                           Authenticator authenticator,
                                           Authorizer authorizer) {
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(realm);
        sm.setAuthenticator(authenticator);
        sm.setAuthorizer(authorizer);
        return sm;
    }

    /**
     * ShiroFilterFactoryBean — 过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);

        // 登录相关 URL
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/drone/list");
        filterFactoryBean.setUnauthorizedUrl("/error/403");

        // 过滤器链定义（顺序敏感 → LinkedHashMap）
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/logout", "logout");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/images/**", "anon");
        filterChainMap.put("/webjars/**", "anon");
        filterChainMap.put("/druid/**", "anon");
        filterChainMap.put("/favicon.ico", "anon");
        filterChainMap.put("/error/**", "anon");
        filterChainMap.put("/**", "authc");

        filterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return filterFactoryBean;
    }

    /**
     * Thymeleaf 中使用 Shiro 标签
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 启用 Shiro 注解（@RequiresPermissions 等）
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
