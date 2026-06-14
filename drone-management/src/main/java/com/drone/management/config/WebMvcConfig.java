package com.drone.management.config;

import com.drone.management.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * - 注册拦截器
 * - 配置视图控制器
 * - 配置静态资源映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 强制 UTF-8 编码过滤器 — 解决中文 Windows 系统默认编码非 UTF-8 导致的中文乱码问题
     *
     * 优先级设为最高，确保在其他 Filter 之前执行。
     * forceEncoding=true 同时覆盖请求和响应的编码，防止表单提交的中文被
     * 按系统默认编码（GBK）解码后变成 U+FFFD 替换字符存入 SQLite。
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> encodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE); // 最高优先级
        registration.setName("characterEncodingFilter");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器：拦截所有请求，排除静态资源
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/images/**",
                        "/webjars/**", "/druid/**",
                        "/favicon.ico");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页跳转
        registry.addViewController("/").setViewName("redirect:/drone/list");
        // Druid 监控 → /druid/index.html
        registry.addViewController("/druid").setViewName("redirect:/druid/index.html");
        // 错误页面快捷访问
        registry.addViewController("/error/400").setViewName("error/400");
        registry.addViewController("/error/403").setViewName("error/403");
        registry.addViewController("/error/404").setViewName("error/404");
        registry.addViewController("/error/500").setViewName("error/500");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // WebJars 静态资源映射
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
