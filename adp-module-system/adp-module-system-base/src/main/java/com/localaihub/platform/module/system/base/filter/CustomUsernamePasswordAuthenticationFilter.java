package com.localaihub.platform.module.system.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localaihub.platform.module.system.base.handler.CustomAuthenticationFailureHandler;
import com.localaihub.platform.module.system.base.service.user.UserService;
import com.localaihub.platform.module.system.base.service.jwt.AuthenticationService;
import com.localaihub.platform.module.system.base.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 23:43
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationService authenticationService;
    private JwtService jwtService;
    private UserService userService;
    private ObjectMapper objectMapper;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;
        try {
            requestBody = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to read request body", e);
        }

        String username = "";
        String password = "";
        try {
            JSONObject jsonData = new JSONObject(requestBody);
            username = jsonData.optString("username");
            password = jsonData.optString("password");
        } catch (Exception e) {
            throw new AuthenticationServiceException("Failed to parse request body", e);
        }
        // 打印用户名和密码到控制台
        System.out.println("认证username: " + username);
        System.out.println("密码Password: " + password);


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}