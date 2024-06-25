package com.localaihub.platform.module.system.base.service.dataset;

import com.localaihub.platform.module.system.base.dao.dataset.DatasetRepository;
import com.localaihub.platform.module.system.base.dao.dataset.FileRepository;
import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:40
 */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService{

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public long countDatasets() {
        return datasetRepository.countDatasets();
    }

    @Override
    public List<Integer> getDailyIncreases(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<Object[]> result = datasetRepository.getDailyIncreases(startDate);
        return result.stream()
                .map(row -> ((Long) row[1]).intValue())
                .collect(Collectors.toList());
    }
    @Override
    public List<DatasetEntity> getAllDatasets() {
        return datasetRepository.findAll();
    }
    @Override
    public Optional<DatasetEntity> getDatasetById(Long id) {
        return datasetRepository.findById(id);
    }
    @Override
    public DatasetEntity saveDataset(DatasetEntity dataset) {
        return datasetRepository.save(dataset);
    }
    @Override
    public void deleteDataset(Long id) {
        datasetRepository.deleteById(id);
    }
    @Override
    public FileEntity saveFile(FileEntity file) {
        return fileRepository.save(file);
    }
    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
    @Override
    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }
    @Override
    public Optional<DatasetEntity> getDatasetByIdAndMd5(Long id, String md5) {
        return datasetRepository.findByIdAndMd5(id, md5);
    }
    @Override
    public String generateMd5(String name, String description) {
        String combined = name + description;
        return DigestUtils.md5Hex(combined);
    }
    private String generateFileMd5(String... inputs) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (String input : inputs) {
                md.update(input.getBytes());
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate MD5 hash", e);
        }
    }
    @Override
    public long calculateDatasetSize(DatasetEntity dataset) {
        if (dataset.getFileEntities() == null) {
            return 0L;
        }
        return dataset.getFileEntities().stream().mapToLong(FileEntity::getSize).sum();
    }
    @Override
    public Optional<DatasetEntity> findById(Long id) {
        return datasetRepository.findById(id);
    }
    @Override
    public List<DatasetEntity> getDatasetsByType(String type) {
        return datasetRepository.findByType(type);
    }
    @Override
    public List<DatasetEntity> getLatestDatasets() {
        return datasetRepository.findTop10ByOrderByCreatedAtDesc();
    }
//    private static final Logger logger = LoggerFactory.getLogger(DatasetService.class);

//    @Override
//    @Async
//    public void trainDataset(DatasetEntity dataset) {
//        // 获取数据集路径
//        String datasetPath = dataset.getPath();
//        // 模型保存路径
//        String modelSavePath = datasetPath + "/gem_classification_model.keras";
//
//        // 调用训练服务（例如，使用 shell 脚本或直接调用 Python 训练脚本）
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    "python3",
//                    "/Users/jiangjiaxing/project/GraduationProject/AIDatasetsPlatform-test/AIDatasetsPlatform/adp-module-system/adp-module-system-base/src/main/java/com/localaihub/platform/module/system/base/util/train.py",
//                    datasetPath,
//                    modelSavePath
//            );
//            Process process = processBuilder.start();
//
//            // 捕获标准输出流
//            new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        logger.info(line);
//                    }
//                } catch (IOException e) {
//                    logger.error("Error reading standard output", e);
//                }
//            }).start();
//
//            // 捕获错误输出流
//            new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        logger.error(line);
//                    }
//                } catch (IOException e) {
//                    logger.error("Error reading error output", e);
//                }
//            }).start();
//
//            // 等待进程完成
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            logger.error("Error during training process", e);
//        }
//    }

}