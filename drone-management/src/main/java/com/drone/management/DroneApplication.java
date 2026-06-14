package com.drone.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 无人机信息管理系统 — 启动类
 *
 * Shiro 自动配置排除项统一在 application.yml 中管理，
 * 避免 shiro-spring-boot-web-starter 与手动 ShiroConfig 冲突。
 */
@SpringBootApplication
public class DroneApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }

}
