package com.localaihub.platform.module.system.base.service.jwt;

import com.localaihub.platform.module.system.base.dao.jwt.JwtAuthenticationResponse;
import com.localaihub.platform.module.system.base.dao.jwt.SignUpRequest;
import com.localaihub.platform.module.system.base.dao.jwt.SigninRequest;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 01:38
 */
public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
