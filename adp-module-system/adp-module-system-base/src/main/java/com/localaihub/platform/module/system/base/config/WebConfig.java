package com.localaihub.platform.module.system.base.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 17:25
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://192.168.3.20:8848", "http://192.168.3.20:8849", "http://localhost:8848", "http://localhost:8849", "http://localaihub.com", "http://127.0.0.1:8849") // 使用模式匹配和特定IP
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "X-Auth-Token", "X-Requested-With")
                .exposedHeaders("X-Auth-Token")
                .allowCredentials(true);
    }

    @Bean
    public Filter cspFilter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                // 设置Content-Security-Policy头部，允许指定来源的iframe嵌入
                httpResponse.setHeader("Content-Security-Policy", "frame-ancestors 'self' http://127.0.0.1:8849 http://192.168.3.20:8848 http://192.168.3.20:8849 http://192.168.1.103:8848 http://192.168.1.103:8849 http://localaihub.com");
                chain.doFilter(request, response);
            }

            @Override
            public void destroy() {
            }
        };
    }
}