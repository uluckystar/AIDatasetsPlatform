package com.localaihub.platform.module.system.base.dao.dataset;

import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:35
 */
public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {
    // Custom query methods can be defined here
    List<FileEntity> findByDatasetEntity(DatasetEntity datasetEntity);
}