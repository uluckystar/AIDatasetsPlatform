package com.localaihub.platform.module.system.base.dao.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 01:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    private String username;
    private String password;
}