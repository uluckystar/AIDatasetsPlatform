package com.localaihub.platform.module.system.base.service.jwt;

import org.springframework.security.core.Authentication;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 16:00
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}