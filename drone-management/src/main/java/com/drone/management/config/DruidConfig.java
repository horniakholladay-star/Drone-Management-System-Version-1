package com.drone.management.config;

import org.springframework.context.annotation.Configuration;

/**
 * Druid 数据源配置
 *
 * 绝大部分 Druid 配置已在 application.yml 中通过 spring.datasource.druid.* 指定，
 * 本类仅保留纯 Java 配置项（如有需要可在此追加自定义 Filter/StatLogger 等）。
 */
@Configuration
public class DruidConfig {

    // Druid 通过 druid-spring-boot-starter 自动配置
    // 详细参数在 application.yml 中:
    //   spring.datasource.druid.initial-size / max-active / validation-query ...

}
