package com.localaihub.platform.module.system.base.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 17:25
 */
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull(message = "角色不能为空!")
    @Size(min = 1, max = 10, message = "角色名称不超过10个字符")
    @Column(nullable = false, unique = true)
    public String name;    //角色名

    @Column(unique = true)
    private String code; // 角色标识

    @Column(nullable = false)
    private Integer status; //角色状态

    @Column(length = 255)
    private String remark; //角色备注

    @ManyToMany(mappedBy = "roles")
//    @JsonManagedReference
//    @JsonIgnoreProperties
    private Set<UserEntity> users; // 角色拥有的用户集合

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = 1; // 设置默认值
        }
    }




    public RoleEntity() {
    }

    public RoleEntity(Integer id, String name, String code, Integer status, String remark, Set<UserEntity> users) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.status = status;
        this.remark = remark;
        this.users = users;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
