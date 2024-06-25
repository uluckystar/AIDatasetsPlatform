//package com.localaihub.platform.gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.ArrayList;
//
///**
// * @author Jiaxing Jiang
// * @version 0.1.0-SNAPSHOT
// * @date 2024/5/13 00:27
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    /**
//     * 密码加密策略
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 鉴权管理器，鉴权交给网关或者资源服务，所以这里不做额外配置，但是需要初始化并注入
//     */
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    /**
//     * http请求访问配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                /**
//                 * 三种写法：
//                 * anyRequest().permitAll() // 放行所有请求
//                 * anyRequest().authenticated() // 所有请求都要认证
//                 * antMatchers("/**").permitAll()   //定制
//                 */
//                .anyRequest().permitAll()
//                .and()
//                // 设置跨域，这里不设置的话，跨域配置不会生效
//                .cors()
//                .and()
//                /**
//                 * 关闭跨站请求保护以及不使用session
//                 * 关闭CSRF防护：使用JWT可以防止CSRF，并且客户端可能不是浏览器，CSRF保护会增加过滤器等其他组件影响性能，所以应该禁用
//                 * 关闭session：使用JWT就可以不用session了，并且客户端可能不是浏览器
//                 */
//                .csrf()
//                .disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        ;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 写入一个测试用户到内存中用于登录，密码是123456
//        UserDetails userDetails = new User("admin", "$2a$10$CzJxwgp4ZAYhxs/9h6MKjeobgYivUDtthxHT3OXB2oCp02UkKgne2", new ArrayList<>());
//        auth.inMemoryAuthentication()
//                .withUser(userDetails).passwordEncoder(passwordEncoder());
//    }
//}