package com.localaihub.platform.module.system.base.service.jwt;

import com.localaihub.platform.module.system.base.dto.RoleDto;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 01:23
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateTokenWithExpiration(new HashMap<>(), userDetails,  12 * 60 * 60 * 1000); // Refresh token有效期12h:12 * 60 * 60 * 1000
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    @Override
    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            // 如果roles为null，返回一个空的授权列表
            return List.of();
        }
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateTokenWithExpiration(new HashMap<>(), userDetails, 7 * 24 * 60 * 60 * 1000); // Refresh token有效期7天:7 * 24 * 60 * 60 * 1000
    }
    @Override
    public String generateTokenWithExpiration(Map<String, Object> extraClaims, UserDetails userDetails, long expirationTime) {
        extraClaims.put("roles", userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    @Override
    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public Set<RoleDto> convertToRoleDTOs(Set<RoleEntity> roles) {
        return roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            return roleDto;
        }).collect(Collectors.toSet());
    }
}