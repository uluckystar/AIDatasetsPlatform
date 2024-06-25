package com.localaihub.platform.module.system.base.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localaihub.platform.framework.common.result.ResultBase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 22:56
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ResultBase resultBase = new ResultBase(false, "Authentication failed: " + exception.getMessage());

        // 将ResultBase对象转换为JSON字符串并写入响应
        String jsonResponse = objectMapper.writeValueAsString(resultBase);
        response.getWriter().write(jsonResponse);
    }
}