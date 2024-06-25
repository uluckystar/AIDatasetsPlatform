package com.localaihub.platform.module.system.base.service.jwt;

import com.localaihub.platform.module.system.base.dao.jwt.JwtAuthenticationResponse;
import com.localaihub.platform.module.system.base.dao.jwt.SignUpRequest;
import com.localaihub.platform.module.system.base.dao.jwt.SigninRequest;
import com.localaihub.platform.module.system.base.dao.user.UserDao;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.user.RoleService;
import com.localaihub.platform.module.system.base.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 01:43
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class); // 创建日志对象，用于记录日志

    @Autowired
    public AuthenticationServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, JwtService jwtService,
                                     AuthenticationManager authenticationManager, RoleService roleService, UserService userService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
        this.userService = userService;
    }
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        // 必填项验证
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPhone() == null || request.getPhone().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("所有字段都是必填项");
        }

        // 格式验证
        if (request.getUsername().length() < 3 || request.getUsername().length() > 20) {
            throw new IllegalArgumentException("用户名长度必须在3到20个字符之间");
        }
        if (!request.getPhone().matches("\\d{10,15}")) {
            throw new IllegalArgumentException("电话号码格式无效");
        }

        // 唯一性验证
        if (userDao.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 密码强度验证
        if (request.getPassword().length() < 8) {
            throw new IllegalArgumentException("密码长度必须至少为8个字符");
        }
        if (!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
            throw new IllegalArgumentException("密码必须包含大小写字母和数字");
        }

        var user = UserEntity.builder()
                .username(request.getUsername())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roleService.getDefaultRoles())
                .createTime(LocalDateTime.now())
                .status(1)
                .build();
        userService.registerNewUser(user);

//        userDao.save(user);

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshToken).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateToken(user);
        logger.debug("当前登录用户的accessToken：" + jwt);
        var refreshToken = jwtService.generateRefreshToken(user);
        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshToken).build();
    }
}