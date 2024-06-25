package com.localaihub.platform.module.system.base.service.dataset;

import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 22:59
 */
public interface FileService {
    void deleteFile(Long id);
    FileEntity saveFile(MultipartFile file, DatasetEntity dataset, String category, List<String> tags, List<String> features);
    FileEntity saveFile(Path filePath, DatasetEntity dataset, String category, List<String> tags, List<String> features);
    String generateMd5(MultipartFile file);
    String generateMd5(Path filePath);
    long calculateFileSize(MultipartFile file);
    Optional<FileEntity> getFileById(Long id);
    List<FileEntity> getFilesByDataset(DatasetEntity dataset);
    String getFileType(String contentType);
}
