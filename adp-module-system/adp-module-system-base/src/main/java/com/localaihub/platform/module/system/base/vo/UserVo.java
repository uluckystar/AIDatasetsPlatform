package com.localaihub.platform.module.system.base.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;
import com.localaihub.platform.module.system.base.util.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 20:53
 */
public class UserVo implements Serializable {

    private Integer id;
    private String username; // 用户名
    private String nickname; // 用户昵称
    private Integer sex; // 性别，例如1代表女，0代表男
    private DepartmentEntity dept; // 归属部门
    private Integer status; // 用户状态，例如1代表已启用， 0代表已停用
    private String phone; // 手机号码
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime; // 创建时间
    private String avatar; // 头像

    public UserVo() {
    }

    public UserVo(Integer id, String username, String nickname, Integer sex, DepartmentEntity dept, Integer status, String phone, LocalDateTime createTime, String avatar) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.sex = sex;
        this.dept = dept;
        this.status = status;
        this.phone = phone;
        this.createTime = createTime;
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVo userVo = (UserVo) o;
        return Objects.equals(id, userVo.id) && Objects.equals(username, userVo.username) && Objects.equals(nickname, userVo.nickname) && Objects.equals(sex, userVo.sex) && Objects.equals(dept, userVo.dept) && Objects.equals(status, userVo.status) && Objects.equals(phone, userVo.phone) && Objects.equals(createTime, userVo.createTime) && Objects.equals(avatar, userVo.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, nickname, sex, dept, status, phone, createTime, avatar);
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", dept=" + dept +
                ", status=" + status +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public DepartmentEntity getDept() {
        return dept;
    }

    public void setDept(DepartmentEntity dept) {
        this.dept = dept;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
