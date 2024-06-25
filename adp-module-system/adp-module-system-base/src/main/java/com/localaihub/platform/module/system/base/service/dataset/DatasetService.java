package com.localaihub.platform.module.system.base.service.dataset;

import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:37
 */
public interface DatasetService {
    long countDatasets();
    List<Integer> getDailyIncreases(int days);
    List<DatasetEntity> getAllDatasets();
    Optional<DatasetEntity> getDatasetById(Long id);
    DatasetEntity saveDataset(DatasetEntity dataset);
    void deleteDataset(Long id);
    FileEntity saveFile(FileEntity file);
    void deleteFile(Long id);
    Optional<FileEntity> getFileById(Long id);
    Optional<DatasetEntity> getDatasetByIdAndMd5(Long id, String md5);
    String generateMd5(String name, String description);
    long calculateDatasetSize(DatasetEntity dataset);
    Optional<DatasetEntity> findById(Long id);
    List<DatasetEntity> getDatasetsByType(String type);
    List<DatasetEntity> getLatestDatasets();
//    void trainDataset(DatasetEntity dataset);

}
