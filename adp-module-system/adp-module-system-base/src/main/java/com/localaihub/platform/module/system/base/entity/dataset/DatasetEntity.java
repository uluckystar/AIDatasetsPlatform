package com.localaihub.platform.module.system.base.entity.dataset;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:31
 */
@Entity
@Table(name = "datasets")
public class DatasetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private boolean isSetup;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String md5;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Column(nullable = false)
    private int downloadCount;

    @Column(nullable = false)
    private long datasetSize;
    @Column(nullable = false)
    private String features;
    @OneToMany(mappedBy = "datasetEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FileEntity> fileEntities = new ArrayList<>();

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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public long getDatasetSize() {
        return datasetSize;
    }

    public void setDatasetSize(long datasetSize) {
        this.datasetSize = datasetSize;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<FileEntity> getFileEntities() {
        return fileEntities;
    }

    public void setFileEntities(List<FileEntity> fileEntities) {
        this.fileEntities = fileEntities;
    }
}