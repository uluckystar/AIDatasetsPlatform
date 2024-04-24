package com.localaihub.platform.service;

/**
 * @author jiang_star
 * @date 2024/3/26
 */
import com.localaihub.platform.model.Dataset;
import com.localaihub.platform.repository.DatasetRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private DatasetRepository datasetRepository;

    @Autowired
    public FileStorageService(DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }

    public String storeFile(MultipartFile file) throws IOException {
        // 获取文件存储目录的绝对路径
        Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        // 确保存储目录存在，如果不存在则创建
        Files.createDirectories(fileStorageLocation);

        // 清理文件名，防止路径遍历攻击
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // 在文件名中添加时间戳以避免名称冲突
        String newFileName =FilenameUtils.getBaseName(fileName) + "_" +
                System.currentTimeMillis() + "." +
                FilenameUtils.getExtension(fileName);
        System.out.println(newFileName);
        // 获取文件的目标存储位置
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Path newTargetLocation = fileStorageLocation.resolve(newFileName);

        // 将上传的文件拷贝到目标存储位置
        Files.copy(file.getInputStream(), newTargetLocation, StandardCopyOption.REPLACE_EXISTING);

        // 创建一个 Dataset 对象来保存文件的元数据
        Dataset dataset = new Dataset();
        dataset.setName(fileName);
        dataset.setPath(newTargetLocation.toString());

        // 调用实例方法save()，需要通过DatasetRepository对象调用
        datasetRepository.save(dataset);

        // 检查文件是否已存在
        if(Files.exists(targetLocation)) {
            return "文件已成功上传:" + newFileName + "但您在早些时候已上传同名文件:" + targetLocation.toString();
        }

        // 返回文件路径或其他标识符
        return targetLocation.toString();
    }

    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            System.out.println("File path: " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

}
