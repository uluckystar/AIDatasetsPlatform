package com.localaihub.platform.module.system.base.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 17:27
 */
public class RoleDto implements Serializable {

    private Integer id;
    private String name;
    private Integer status; //角色状态


    public RoleDto() {
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return status == roleDto.status && Objects.equals(id, roleDto.id) && Objects.equals(name, roleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RoleDto(Integer id, String name, Integer status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
