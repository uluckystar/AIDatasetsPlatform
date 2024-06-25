package com.localaihub.platform.module.system.base.vo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 11:42
 */
public class DatasetVo {

    private Long id;
    private String name;
    private String description;
    private String type;
    private boolean isSetup;
    private String path;
    private String md5;
    private Date createdAt;
    private Date updatedAt;
    private String owner;  // owner 是字符串类型
    private int downloadCount;
    private String datasetSize;
    private Set<String> usagePermissions;
    private String features;
    private List<FileVo> fileEntities;  // 假设你有一个 FileVO 类来表示文件实体
    private String moduleInfo;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsSetup() {
        return isSetup;
    }

    public void setIsSetup(boolean setup) {
        isSetup = setup;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getDatasetSize() {
        return datasetSize;
    }

    public void setDatasetSize(String datasetSize) {
        this.datasetSize = datasetSize;
    }

    public Set<String> getUsagePermissions() {
        return usagePermissions;
    }

    public void setUsagePermissions(Set<String> usagePermissions) {
        this.usagePermissions = usagePermissions;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<FileVo> getFileEntities() {
        return fileEntities;
    }

    public void setFileEntities(List<FileVo> fileEntities) {
        this.fileEntities = fileEntities;
    }

    public String getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(String moduleInfo) {
        this.moduleInfo = moduleInfo;
    }
}