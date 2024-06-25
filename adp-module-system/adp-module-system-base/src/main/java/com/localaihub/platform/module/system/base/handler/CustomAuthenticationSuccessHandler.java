package com.localaihub.platform.module.system.base.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localaihub.platform.framework.common.result.jwt.ResultToken;
import com.localaihub.platform.framework.common.result.jwt.ResultUser;
import com.localaihub.platform.module.system.base.dao.jwt.JwtAuthenticationResponse;
import com.localaihub.platform.module.system.base.dao.jwt.SigninRequest;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.user.UserService;
import com.localaihub.platform.module.system.base.service.jwt.AuthenticationService;
import com.localaihub.platform.module.system.base.service.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/15 11:23
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public CustomAuthenticationSuccessHandler(AuthenticationService authenticationService,
                                              JwtService jwtService,
                                              UserService userService,
                                              ObjectMapper objectMapper) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Suppose the user details are being set in the authentication principal
        String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

        // Proceed with the authentication success logic
        JwtAuthenticationResponse jwtResponse = authenticationService.signin(new SigninRequest(username, null));  // Adjust according to actual method signature

        Date expirationTime = jwtService.extractExpiration(jwtResponse.getAccessToken());
        UserEntity userEntity = userService.findByUsername(username);
        Set<RoleEntity> roleEntities = userEntity.getRoles();
        String[] roleNames = roleEntities.stream().map(RoleEntity::getName).toArray(String[]::new);

        ResultUser resultUser = new ResultUser(true, new ResultToken(jwtResponse.getAccessToken(), expirationTime, jwtResponse.getRefreshToken(), userEntity.getAvatar(), username, userEntity.getNickname(), roleNames));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(resultUser));
        response.getWriter().flush();
    }
}