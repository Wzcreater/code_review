package com.tphy.peis.entity.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ProviderMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 项目中的所有接口都支持跨域
        registry.addMapping("/**")
                //允许哪些域能访问我们的跨域资源
                .allowedOrigins("*")
                //允许的访问方法"POST", "GET", "PUT", "OPTIONS", "DELETE"等
                .allowedMethods("*")
                //允许所有的请求header访问，可以自定义设置任意请求头信息
                .allowedHeaders("*");
    }
}

