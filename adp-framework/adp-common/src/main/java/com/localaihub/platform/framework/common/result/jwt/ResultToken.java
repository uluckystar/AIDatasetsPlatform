package com.localaihub.platform.framework.common.result.jwt;

import java.util.Date;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 19:54
 */
public class ResultToken {
    /** 访问令牌 */
    private String accessToken;
    /** 访问令牌的过期时间（时间戳） */
    private Date expires;
    /** 刷新访问令牌时所需的令牌 */
    private String refreshToken;
    /** 头像 */
    private String avatar;
    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 当前登录用户的角色 */
    private String[] roles;

    public ResultToken(String accessToken, Date expires, String refreshToken, String avatar, String username, String nickname, String[] roles) {
        this.accessToken = accessToken;
        this.expires = expires;
        this.refreshToken = refreshToken;
        this.avatar = avatar;
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}

