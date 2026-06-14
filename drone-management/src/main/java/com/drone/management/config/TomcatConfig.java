package com.drone.management.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tomcat 容器配置
 *
 * 允许 URL 路径中的分号 (;)，防止 ;jsessionid= 路径参数被 Tomcat 拒绝导致 400 错误。
 * 配合 server.servlet.session.tracking-modes=cookie 双保险：
 *   - tracking-modes=cookie 从源头禁用 URL 重写
 *   - 此处作为兜底，兼容含分号的旧链接或外部请求
 */
@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatSemicolonCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // Tomcat 9.0.x 允许分号路径参数
            // connector.setProperty 作用于 ProtocolHandler，需通过 getProperty 确认支持
            connector.setProperty("ALLOW_SEMICOLON", "true");

            // 备选：直接设置系统属性（部分 Tomcat 版本需要）
            System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_SEMICOLON", "true");
        });
    }
}
