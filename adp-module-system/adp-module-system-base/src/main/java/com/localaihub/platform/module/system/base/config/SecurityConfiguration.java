package com.localaihub.platform.module.system.base.config;

// 导入JWT认证过滤器，用于在请求处理链中拦截和处理JWT令牌

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localaihub.platform.module.system.base.filter.CustomUsernamePasswordAuthenticationFilter;
import com.localaihub.platform.module.system.base.filter.JwtAuthenticationFilter;
import com.localaihub.platform.module.system.base.handler.CustomAuthenticationFailureHandler;
import com.localaihub.platform.module.system.base.handler.CustomAuthenticationSuccessHandler;
import com.localaihub.platform.module.system.base.service.user.UserService;
import com.localaihub.platform.module.system.base.service.jwt.AuthenticationService;
import com.localaihub.platform.module.system.base.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * 配置Spring Security，保护Web应用的安全。
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 23:14
 */
@Configuration
@EnableWebSecurity  // 启用Spring Security的Web安全功能
@RequiredArgsConstructor // 使用Lombok生成构造函数，自动注入final字段
public class SecurityConfiguration {
    // 声明JWT认证过滤器，用于处理带有JWT的HTTP请求
    private final JwtService jwtService;
    // 声明用户服务，提供用户相关操作的接口
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;  // 注入PasswordEncoder bean
    private final CorsConfigurationSource corsConfigurationSource;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;

    @Bean  // 声明一个Bean，用于Spring容器管理
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 禁用CSRF保护，因为使用token机制不需要CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource))  // 启用CORS配置
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/auth/**","swagger-ui/**","/v3/**","/app/files/preview/**").permitAll()  // 无条件访问
//                        .requestMatchers("/app/**").hasAnyRole("ADMIN", "SUPER_ADMIN","USER")  // 仅ADMIN和SUPER_ADMIN角色可以访问
                        .requestMatchers("/app/**").authenticated() // 所有经过认证的用户均可访问
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")  // 仅ADMIN和SUPER_ADMIN角色可以访问
//                        .requestMatchers("/admin/**").permitAll()  // 无条件访问
                        .requestMatchers("/supe/radmin/**").hasRole("SUPER_ADMIN")  // 仅SUPER_ADMIN角色可以访问
                        .anyRequest().authenticated())  // 要求所有其他请求都必须经过认证
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))  // 设置会话管理策略为STATELESS，提升无状态接口性能
                .authenticationProvider(authenticationProvider())  // 指定自定义的认证提供者
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)  // 添加自定义的认证过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> {
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        System.out.println("Access denied for user: " + SecurityContextHolder.getContext().getAuthentication());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    });
                });  // 添加 JWT 认证过滤器
        return http.build();  // 构建并返回配置好的SecurityFilterChain对象
    }

    @Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager, customAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(authenticationService, jwtService, userService, objectMapper));
        return filter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();  // 创建一个DAO认证提供者
        authProvider.setUserDetailsService(userService.userDetailsService());  // 设置用户服务，用于加载用户信息
        authProvider.setPasswordEncoder(passwordEncoder);  // 设置密码编码器，用于在认证过程中比对密码
        return authProvider;  // 返回配置好的认证提供者
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();  // 从AuthenticationConfiguration获取AuthenticationManager，用于全局认证配置
    }
}
