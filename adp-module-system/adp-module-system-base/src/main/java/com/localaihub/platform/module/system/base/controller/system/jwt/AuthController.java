package com.localaihub.platform.module.system.base.controller.system.jwt;

import com.localaihub.platform.framework.common.result.jwt.ResultToken;
import com.localaihub.platform.framework.common.result.jwt.ResultUser;
import com.localaihub.platform.module.system.base.dao.jwt.ErrorResponse;
import com.localaihub.platform.module.system.base.dao.jwt.JwtAuthenticationResponse;
import com.localaihub.platform.module.system.base.dao.jwt.SignUpRequest;
import com.localaihub.platform.module.system.base.dao.jwt.SigninRequest;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.jwt.AuthenticationService;
import com.localaihub.platform.module.system.base.service.jwt.JwtService;
import com.localaihub.platform.module.system.base.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 认证控制器，处理注册、登录和令牌刷新请求。
 *
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 13:11
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth-jwt管理", description = "处理用户注册、登录和令牌刷新操作")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    /**
     * 注册新用户。
     *
     * @param request 包含用户名、密码等信息的注册请求对象。
     * @return 注册结果，包括JWT认证信息。
     */
    @Operation(summary = "用户注册", description = "注册一个新用户并返回JWT认证信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "注册成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "请求无效")
    })
    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        try {
            JwtAuthenticationResponse response = authenticationService.signup(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("400", e.getMessage()));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("409", e.getMessage()));
        }
    }

    /**
     * 用户登录。
     *
     * @param request 包含用户名和密码的登录请求对象。
     * @return 包含用户信息和JWT令牌的结果对象。
     */
    @Operation(summary = "用户登录", description = "用户登录并返回JWT令牌和用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultUser.class))),
            @ApiResponse(responseCode = "401", description = "认证失败")
    })
    @PostMapping("/login")
    public ResultUser signin(@RequestBody SigninRequest request) {
        JwtAuthenticationResponse response = authenticationService.signin(request);
        Date expirationTime = jwtService.extractExpiration(response.getAccessToken());
        UserEntity userEntity = userService.findByUsername(request.getUsername());
        Set<RoleEntity> roleEntities = userEntity.getRoles();
        String[] roleNames = roleEntities.stream()
                .map(RoleEntity::getName)
                .toArray(String[]::new);
        return new ResultUser(true, new ResultToken(response.getAccessToken(), expirationTime,
                response.getRefreshToken(), userEntity.getAvatar(), request.getUsername(),
                userEntity.getNickname(), roleNames));
    }

    /**
     * 刷新JWT令牌。
     *
     * @param request 用于刷新令牌的刷新令牌字符串。
     * @return 包含新JWT令牌和刷新令牌的认证响应对象。
     */
    @Operation(summary = "刷新JWT令牌", description = "使用刷新令牌获取新的JWT令牌")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "刷新成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "刷新令牌无效")
    })
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token is missing");
        }
        String username = jwtService.extractUserName(refreshToken);
        UserDetails user = userService.loadUserByUsername(username);
        if (jwtService.isTokenValid(refreshToken, user)) {
            String newJwt = jwtService.generateToken(user);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            Map<String, Object> data = new HashMap<>();
            data.put("accessToken", newJwt);
            data.put("refreshToken", refreshToken);
            data.put("expires", jwtService.extractExpiration(newJwt)); // 确保这里返回的是 Date 类型
            response.put("data", data);
            return ResponseEntity.ok(response);
        }
        throw new RuntimeException("Invalid refresh token");
    }
}
