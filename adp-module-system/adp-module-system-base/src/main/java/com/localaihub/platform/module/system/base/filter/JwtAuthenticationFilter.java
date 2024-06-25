package com.localaihub.platform.module.system.base.filter;

import com.localaihub.platform.module.system.base.handler.CustomAuthenticationFailureHandler;
import com.localaihub.platform.module.system.base.service.jwt.JwtService;
import com.localaihub.platform.module.system.base.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 02:09
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(@Lazy UserService userService, JwtService jwtService, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.userService = userService;
        this.jwtService =  jwtService;
        this.customAuthenticationFailureHandler =  customAuthenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 从请求头中获取Authorization字段
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 检查Authorization字段是否为空，或是否以"Bearer "开头
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 提取JWT
        jwt = authHeader.substring(7);


        try {
            // 从JWT中提取用户名
            username = jwtService.extractUserName(jwt);

            // 验证用户名不为空并且当前没有已认证的用户
            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 从数据库中加载用户详细信息
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
                // 验证JWT是否有效
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // 创建认证对象并设置到SecurityContext
//                    List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(jwt);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    System.out.println(userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // 继续过滤器链
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, ex);
        }
    }
}