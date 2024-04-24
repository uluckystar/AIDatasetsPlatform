package com.localaihub.platform.config;

//import com.localaihub.platform.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author jiang_star
 * @date 2024/3/26
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // 定义一个SecurityFilterChain Bean，该Bean配置了Spring Security的安全过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 开始配置HTTP请求的安全规则
        // @formatter:off
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                // 对所有HTTP请求进行授权，要求所有请求都需要经过身份验证
                .authorizeHttpRequests((authorize) -> authorize
                                .dispatcherTypeMatchers(HttpMethod.valueOf("/register")).permitAll() // 允许注册页面无需认证
//                        .requestMatchers("/api/datasets/upload").hasAuthority("ROLE_USER") // 仅允许USER角色访问上传
                        .anyRequest().authenticated()
                )
                // 启用基本的HTTP身份验证
                .httpBasic(withDefaults())
                // 启用基于表单的登录
//                .formLogin(withDefaults());
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );
//         @formatter:on
//         构建并返回SecurityFilterChain实例
        return http.build();
    }

    // 定义一个内存中的用户详细信息管理器Bean，其中包含一个用户名为"user"、密码为"password"、角色为"USER"的用户
    // @formatter:off
    @Bean
    public InMemoryUserDetailsManager userDetailsService(@Autowired PasswordEncoder passwordEncoder) {
        // 直接使用passwordEncoder来加密密码
        String encodedPassword = passwordEncoder.encode("aa");

        UserDetails user = User.builder()
                .username("user")
                .password(encodedPassword) // 使用加密后的密码
                .roles("USER") // 指定用户角色
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // @formatter:on

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
