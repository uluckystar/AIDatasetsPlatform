package com.localaihub.platform.module.system.base.init;

import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.user.RoleService;
import com.localaihub.platform.module.system.base.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 16:24
 */
@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final UserService userService;
    private final RoleService roleService;

    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Initializing roles and super admin user...");

        // 初始化超级管理员角色
        RoleEntity superAdminRole = roleService.findByName("ROLE_SUPER_ADMIN")
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName("ROLE_SUPER_ADMIN");
                    roleService.save(role);
                    logger.info("Created SUPER_ADMIN role");
                    return role;
                });

        // 初始化管理员角色
        RoleEntity adminRole = roleService.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName("ROLE_ADMIN");
                    roleService.save(role);
                    logger.info("Created ADMIN role");
                    return role;
                });

        // 检查是否已经存在超级管理员
        if (!userService.existsByUsername("superadmin")) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(roleService.findByName("ROLE_SUPER_ADMIN").orElseThrow());

            UserEntity superAdmin = UserEntity.builder()
                    .username("superadmin")
                    .phone("19862976718")
                    .password(userService.encodePassword("admin123"))
                    .roles(roles)
                    .build();

            userService.save(superAdmin);
            logger.info("Created superadmin user");
        } else {
            logger.info("superadmin user already exists");
        }
    }
}