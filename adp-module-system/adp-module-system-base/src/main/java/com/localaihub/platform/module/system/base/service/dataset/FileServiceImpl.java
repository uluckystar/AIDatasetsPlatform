package com.localaihub.platform.module.system.base.service.dataset;

import com.localaihub.platform.module.system.base.dao.dataset.FileRepository;
import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 22:59
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public FileEntity saveFile(MultipartFile file, DatasetEntity dataset, String category, List<String> tags, List<String> features) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setType(getFileType(file.getContentType()));
        fileEntity.setSize(file.getSize());
        fileEntity.setPath(dataset.getPath() + "/" + file.getOriginalFilename());
        fileEntity.setMd5(generateMd5(file));
        fileEntity.setDatasetEntity(dataset);
        fileEntity.setCategory(category);
        fileEntity.setTags(tags);
        fileEntity.setFeatures(features);

        return fileRepository.save(fileEntity);
    }
    @Override
    // 新增的方法，支持Path类型的文件
    public FileEntity saveFile(Path filePath, DatasetEntity dataset, String category, List<String> tags, List<String> features) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(filePath.getFileName().toString());
        try {
            fileEntity.setType(getFileType(Files.probeContentType(filePath)));
            fileEntity.setSize(Files.size(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get file details", e);
        }
        fileEntity.setPath(filePath.toString());
        fileEntity.setMd5(generateMd5(filePath));
        fileEntity.setDatasetEntity(dataset);
        fileEntity.setCategory(category);
        fileEntity.setTags(tags);
        fileEntity.setFeatures(features);

        return fileRepository.save(fileEntity);
    }

    @Override
    public String generateMd5(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate MD5 hash", e);
        }
    }
    // 新增的方法，支持Path类型的文件
    @Override
    public String generateMd5(Path filePath) {
        try (InputStream is = Files.newInputStream(filePath)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate MD5 hash", e);
        }
    }


    @Override
    public long calculateFileSize(MultipartFile file) {
        return file.getSize();
    }
    @Override
    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }
    @Override
    public List<FileEntity> getFilesByDataset(DatasetEntity dataset) {
        return fileRepository.findByDatasetEntity(dataset);
    }
    @Override
    public String getFileType(String contentType) {
        if (contentType == null) {
            return "未知";
        }
        switch (contentType) {
            case "image/jpeg":
            case "image/png":
            case "image/gif":
                return "图像";
            case "audio/mpeg":
            case "audio/wav":
                return "音频";
            case "video/mp4":
                return "视频";
            case "text/plain":
            case "application/pdf":
                return "文本";
            default:
                return "其他";
        }
    }
}
