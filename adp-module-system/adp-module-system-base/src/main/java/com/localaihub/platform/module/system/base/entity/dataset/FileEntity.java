package com.localaihub.platform.module.system.base.entity.dataset;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:33
 */
@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String md5;

    @Column(nullable = false)
    private String category;

    @ElementCollection
    @Column(name = "tags")
    private List<String> tags;

    @ElementCollection
    @Column(name = "features")
    private List<String> features;

    @ManyToOne
    @JoinColumn(name = "dataset_id", nullable = false)
    @JsonBackReference
    private DatasetEntity datasetEntity;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public DatasetEntity getDatasetEntity() {
        return datasetEntity;
    }

    public void setDatasetEntity(DatasetEntity datasetEntity) {
        this.datasetEntity = datasetEntity;
    }
}