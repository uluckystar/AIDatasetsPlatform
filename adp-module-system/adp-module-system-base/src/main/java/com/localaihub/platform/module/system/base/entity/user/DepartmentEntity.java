package com.localaihub.platform.module.system.base.entity.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.localaihub.platform.module.system.base.util.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/12 15:27
 */
@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "部门名称不能为空")
    private String name;

    @Column(nullable = true)
    private Integer parentId; // Set nullable as true if parent can be null (i.e., top-level department)

    @NotNull(message = "排序字段不能为空")
    private Integer sort;

    @Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "负责人不能为空")
    private String principal;

    private String email;

    private String remark;

    @NotNull(message = "状态不能为空")
    private Integer status;

    @NotNull(message = "类型不能为空")
    private Integer type;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    // Constructors, Getters and Setters

    public DepartmentEntity() {
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}