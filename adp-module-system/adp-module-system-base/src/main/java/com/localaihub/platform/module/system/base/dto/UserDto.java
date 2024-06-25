package com.localaihub.platform.module.system.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;
import com.localaihub.platform.module.system.base.util.LocalDateTimeSerializer;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 17:20
 */

public class UserDto implements Serializable {

    private Integer id;
    @Size(min = 1, max = 10, message = "用户名不能超过10个字")
    private String username;
    private String password;
//    @Schema(description = "用户拥有的角色列表")
//    private List<RoleDto> roleDtoList;
    private String nickname; // 用户昵称
    private String email; // 邮箱
    private String phone; // 手机号码
    private Integer sex; // 性别，例如1代表女，0代表男
    private DepartmentEntity dept; // 归属部门
    private String description; // 简介
    private String remark; // 备注
    private String avatar; // 头像
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime; // 创建时间
    private Set<RoleDto> roles; // 用户角色
    private Integer status; // 用户状态，例如1代表已启用， 0代表已停用

    public UserDto() {
    }

    public UserDto(Integer id, String username, String password, String nickname, String email, String phone, Integer sex, DepartmentEntity dept, String description, String remark, String avatar, Set<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.dept = dept;
        this.description = description;
        this.remark = remark;
        this.avatar = avatar;
        this.roles = roles;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", department=" + dept +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(nickname, userDto.nickname) && Objects.equals(email, userDto.email) && Objects.equals(phone, userDto.phone) && Objects.equals(sex, userDto.sex) && Objects.equals(dept, userDto.dept) && Objects.equals(description, userDto.description) && Objects.equals(remark, userDto.remark) && Objects.equals(avatar, userDto.avatar) && Objects.equals(roles, userDto.roles) && Objects.equals(status, userDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nickname, email, phone, sex, dept, description, remark, avatar, roles, status);
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

    public DepartmentEntity getDept() {
        return dept;
    }

    public void setDept(DepartmentEntity dept) {
        this.dept = dept;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;  // 在保存用户前确保密码已编码
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
