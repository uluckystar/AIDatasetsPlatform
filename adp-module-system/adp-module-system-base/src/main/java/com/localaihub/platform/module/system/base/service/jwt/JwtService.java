package com.localaihub.platform.module.system.base.service.jwt;

import com.localaihub.platform.module.system.base.dto.RoleDto;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 01:22
 */
public interface JwtService {

    /**
     * 从 JWT 令牌中提取用户名。
     * @param token JWT 令牌
     * @return 提取的用户名
     */
    String extractUserName(String token);

    /**
     * 根据用户详细信息生成 JWT 令牌。
     * @param userDetails 用户详细信息
     * @return 生成的 JWT 令牌
     */
    String generateToken(UserDetails userDetails);

    /**
     * 验证 JWT 令牌是否有效。
     * @param token JWT 令牌
     * @param userDetails 用户详细信息
     * @return 如果令牌有效，则返回 true，否则返回 false
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * 从 JWT 令牌中提取指定声明。
     * @param token JWT 令牌
     * @param claimsResolvers 用于提取声明的函数
     * @param <T> 声明的类型
     * @return 提取的声明
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolvers);

    /**
     *
     * @param token
     * @return
     */
     List<SimpleGrantedAuthority> extractAuthorities(String token);

    /**
     * 生成刷新令牌。
     * @param userDetails 用户详细信息
     * @return 生成的刷新令牌
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * 根据额外声明、用户详细信息和过期时间生成 JWT 令牌。
     * @param extraClaims 额外的声明
     * @param userDetails 用户详细信息
     * @param expirationTime 过期时间
     * @return 生成的 JWT 令牌
     */
    String generateTokenWithExpiration(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime);

    /**
     * 检查 JWT 令牌是否已过期。
     * @param token JWT 令牌
     * @return 如果 JWT 令牌已过期，则返回 true，否则返回 false
     */
    boolean isTokenExpired(String token);

    /**
     * 从 JWT 令牌中提取过期时间。
     * @param token JWT 令牌
     * @return 提取的过期时间
     */
    Date extractExpiration(String token);

    /**
     * 从 JWT 令牌中提取所有声明。
     * @param token JWT 令牌
     * @return 提取的所有声明
     */
    Claims extractAllClaims(String token);

    /**
     * 获取用于签名的密钥。
     * @return 用于签名的密钥
     */
    Key getSigningKey();

    Set<RoleDto> convertToRoleDTOs(Set<RoleEntity> roles);
}
