package com.localaihub.platform.module.system.base.vo;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 11:55
 */
public class FileVo {

    private Long id;
    private String name;
    private String path;
    // 其他字段...

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // 其他字段的 getters 和 setters...
}