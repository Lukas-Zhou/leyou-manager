package com.leyou.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfigration {

    @Bean
    public CorsFilter corsFilter() {
        // 初始化 cors 配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://manage.leyou.com"); //允许的域
        config.setAllowCredentials(true); //是否发送 Cookie 信息
        config.addAllowedMethod("*"); //允许的请求方式
        config.addAllowedHeader("*"); //允许的头信息

        //初始化 cors 配置源对象
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config); //添加映射路径，拦截一切请求

        return new CorsFilter(configSource); //返回 CorsFilter
    }
}


