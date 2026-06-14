package com.drone.management.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro 自定义 Realm
 */
public class DroneRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(DroneRealm.class);

    // 演示用硬编码账号
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin123";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 演示：所有已认证用户均拥有全部权限
        info.addRole("admin");
        info.addStringPermission("*:*:*");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        log.info("Shiro 认证尝试: username={}", username);

        if (!DEMO_USERNAME.equals(username)) {
            throw new UnknownAccountException("用户名或密码错误");
        }

        // 返回认证信息，Shiro 会自动校验密码
        return new SimpleAuthenticationInfo(username, DEMO_PASSWORD, getName());
    }
}
