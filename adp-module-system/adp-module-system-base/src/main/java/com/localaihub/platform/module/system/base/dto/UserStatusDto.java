package com.localaihub.platform.module.system.base.dto;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/17 11:04
 */
public class UserStatusDto {
    private Integer id;
    private Integer status; // 用户状态，例如1代表已启用， 0代表已停用

    public UserStatusDto(){}

    public UserStatusDto(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
