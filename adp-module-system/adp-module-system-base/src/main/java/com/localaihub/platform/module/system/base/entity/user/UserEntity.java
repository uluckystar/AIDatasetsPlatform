package com.localaihub.platform.module.system.base.entity.user;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 16:32
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.localaihub.platform.module.system.base.convert.DtoConverter;
import com.localaihub.platform.module.system.base.dto.UserDto;
import com.localaihub.platform.module.system.base.util.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Singular;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "用户名不能为空!")
    @Size(min = 1, max = 50, message = "用户名长度必须在1到50个字符之间")
    private String username; // 用户名

    @NotEmpty(message = "密码不能为空！")
    @Size(min = 6, max = 100, message = "密码长度必须在6到100个字符之间")
    private String password; // 密码

    @NotEmpty(message = "用户昵称不能为空")
    @Size(min = 1, max = 50, message = "用户昵称长度必须在1到50个字符之间")
    private String nickname; // 用户昵称

    @Email(message = "邮箱格式不正确")
    private String email; // 邮箱

    @Pattern(regexp = "^\\d{11}$", message = "手机号必须是11位数字")
    private String phone; // 手机号码

    @NotNull(message = "性别不能为空")
    private Integer sex; // 性别，例如1代表女，0代表男

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    @JsonProperty("dept")
    private DepartmentEntity dept; // 归属部门

    @NotNull(message = "用户状态不能为空")
    private Integer status; // 用户状态，例如1代表已启用， 0代表已停用
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(updatable = false)
    private LocalDateTime createTime; // 创建时间

    @Column(length = 500)
    private String description; // 简介

    private String remark; // 备注

    private String avatar; // 头像

    // 定义用户和角色的多对多关系
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role", // 指定关联表的表名
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), // 指定本方在中间表的外键
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // 指定对方方在中间表的外键
    )
    @Singular
//    @JsonBackReference
    private Set<RoleEntity> roles; // 用户拥有的角色集合


    //UserDetails 返回用户验证数据接口实现
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public UserEntity() {}

    @Builder
    public UserEntity(String username, String password, String nickname, String phone, Set<RoleEntity> roles, Integer sex, Integer status, DepartmentEntity dept, String remark, String email, LocalDateTime createTime) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.roles = roles;
        this.status = status;
        this.sex = sex;
        this.remark = remark;
        this.dept = dept;
        this.email = email;
        this.createTime = createTime;
    }

    public UserEntity(UserDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.nickname = userDto.getNickname();
        this.email = userDto.getEmail();
        this.phone = userDto.getPhone();
        this.sex = userDto.getSex();
        this.dept = userDto.getDept();
        this.description = userDto.getDescription();
        this.remark = userDto.getRemark();
        this.avatar = userDto.getAvatar();
        this.roles = userDto.getRoles().stream()
                .map(DtoConverter::convertRoleDtoToRoleEntity)
                .collect(Collectors.toSet());
        this.status = userDto.getStatus();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", department=" + dept +
                ", status=" + status +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        long seconds = createTime.toEpochSecond(java.time.ZoneOffset.UTC);
        this.createTime = LocalDateTime.ofEpochSecond(seconds / 1000, 0, java.time.ZoneOffset.UTC);
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

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
