package com.localaihub.platform.module.system.base.controller.dataset;

import com.localaihub.platform.module.system.base.convert.DtoConverter;
import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.dataset.DatasetService;
import com.localaihub.platform.module.system.base.service.dataset.OpenaiService;
import com.localaihub.platform.module.system.base.service.user.UserService;
import com.localaihub.platform.module.system.base.vo.DatasetVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:43
 */
@RestController
@RequestMapping("/app/datasets")
@Tag(name = "数据集管理", description = "管理数据集的CRUD操作")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private UserService userService;

    @Autowired
    private OpenaiService openaiService;

    @Value("${file.upload-dir}")
    private String path;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(path).toAbsolutePath().normalize();
    }

    @Operation(summary = "获取所有数据集", description = "返回所有数据集的列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取数据集列表",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) })
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDatasets() {
        List<DatasetEntity> datasets = datasetService.getAllDatasets();
        List<DatasetVo> datasetVOs = datasets.stream()
                .map(DtoConverter::convertDatasetEntityToDatasetVo)
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", Map.of("list", datasetVOs));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "创建数据集", description = "创建一个新的数据集")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功创建数据集",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) }),
            @ApiResponse(responseCode = "500", description = "创建数据集目录失败",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDataset(@RequestBody DatasetEntity dataset) {
        // 创建数据集目录
        Path datasetPath = rootLocation.resolve(dataset.getName()).normalize();

        dataset.setCreatedAt(new Date());
        dataset.setUpdatedAt(new Date());
        dataset.setPath(datasetPath.toString()); // 设置数据集路径
        dataset.setDownloadCount(0); // 设置默认下载次数为0
        dataset.setDownloadCount(0); // 设置默认下载次数为0
        dataset.setMd5(datasetService.generateMd5(dataset.getName(), dataset.getDescription()));
        dataset.setDatasetSize(datasetService.calculateDatasetSize(dataset)); // 计算数据集大小

        try {
            Files.createDirectories(datasetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Failed to create dataset directory"));
        }

        // 安全的获取创建数据集的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = userService.findByUsername(authentication.getName());
        dataset.setOwner(currentUser);

        // 确保 fileEntities 被正确初始化
        if (dataset.getFileEntities() == null) {
            dataset.setFileEntities(new ArrayList<>());
        }

        DatasetEntity savedDataset = datasetService.saveDataset(dataset);
        DatasetVo datasetVo = DtoConverter.convertDatasetEntityToDatasetVo(savedDataset);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", datasetVo);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "更新数据集", description = "根据ID更新数据集的信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功更新数据集",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) }),
            @ApiResponse(responseCode = "404", description = "数据集未找到",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DatasetVo> updateDataset(@PathVariable Long id, @RequestBody DatasetEntity datasetDetails) {
        Optional<DatasetEntity> dataset = datasetService.getDatasetById(id);
        if (dataset.isPresent()) {
            DatasetEntity updatedDataset = dataset.get();

            updatedDataset.setName(datasetDetails.getName());
            updatedDataset.setDescription(datasetDetails.getDescription());
            updatedDataset.setType(datasetDetails.getType());
            updatedDataset.setIsSetup(datasetDetails.getIsSetup());
            updatedDataset.setPath(datasetDetails.getPath());
            updatedDataset.setUpdatedAt(new Date());
            updatedDataset.setMd5(datasetService.generateMd5(datasetDetails.getName(), datasetDetails.getDescription()));
            updatedDataset.setFeatures(datasetDetails.getFeatures()); // 设置 features
            updatedDataset.setDatasetSize(datasetService.calculateDatasetSize(updatedDataset)); // 计算数据集大小
            // 保留原有的 owner，不更改
            updatedDataset.setOwner(dataset.get().getOwner());

            // 确保 fileEntities 被正确初始化
            if (updatedDataset.getFileEntities() == null) {
                updatedDataset.setFileEntities(new ArrayList<>());
            }

            DatasetEntity savedDataset = datasetService.saveDataset(updatedDataset);
            DatasetVo datasetVo = DtoConverter.convertDatasetEntityToDatasetVo(savedDataset);
            return ResponseEntity.ok(datasetVo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "删除数据集", description = "根据ID删除数据集")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "成功删除数据集",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "数据集未找到",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataset(@PathVariable Long id) {
        Optional<DatasetEntity> datasetOptional = datasetService.getDatasetById(id);
        if (datasetOptional.isPresent()) {
            DatasetEntity dataset = datasetOptional.get();
            List<FileEntity> files = dataset.getFileEntities();
            for (FileEntity file : files) {
                Path filePath = Paths.get(file.getPath());
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            datasetService.deleteDataset(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "下载数据集", description = "根据ID下载数据集")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功下载数据集",
                    content = { @Content(mediaType = "application/octet-stream") }),
            @ApiResponse(responseCode = "404", description = "数据集未找到",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "创建压缩文件失败",
                    content = @Content)
    })
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDataset(@PathVariable Long id) {
        Optional<DatasetEntity> datasetOptional = datasetService.getDatasetById(id);
        if (datasetOptional.isPresent()) {
            DatasetEntity dataset = datasetOptional.get();
            Path datasetPath = rootLocation.resolve(dataset.getName()).normalize();

            // Check if a zip file already exists
            Path zipPath = datasetPath.resolve(dataset.getName() + ".zip");
            if (!Files.exists(zipPath)) {
                try {
                    // Create a zip file if it doesn't exist
                    URI uri = URI.create("jar:" + zipPath.toUri().toString());
                    try (FileSystem zipfs = FileSystems.newFileSystem(uri, Map.of("create", "true"))) {
                        Files.walk(datasetPath).forEach(path -> {
                            Path relativePath = datasetPath.relativize(path);
                            try {
                                if (Files.isDirectory(path)) {
                                    Files.createDirectories(zipfs.getPath(relativePath.toString()));
                                } else {
                                    Files.copy(path, zipfs.getPath(relativePath.toString()), StandardCopyOption.REPLACE_EXISTING);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(500).build();
                }
            }

            try {
                Resource resource = new UrlResource(zipPath.toUri());
                // 使用URL编码处理文件名
                String encodedFileName;
                try {
                    encodedFileName = URLEncoder.encode(zipPath.getFileName().toString(), StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    // 如果编码不支持，则返回500错误
                    e.printStackTrace();
                    return ResponseEntity.status(500).build();
                }
                long contentLength = Files.size(zipPath);

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                        .body(resource);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "按类型获取数据集", description = "根据类型获取数据集列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取数据集列表",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) })
    })
    @GetMapping("/type")
    public ResponseEntity getDatasetsByType(@RequestParam String type) {
        List<DatasetEntity> datasets =  datasetService.getDatasetsByType(type);
        List<DatasetVo> datasetVOs = datasets.stream()
                .map(DtoConverter::convertDatasetEntityToDatasetVo)
                .peek(datasetVo -> datasetVo.setFileEntities(null)) // 去掉 fileEntities 字段
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", Map.of("list", datasetVOs));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "根据ID和MD5获取数据集信息", description = "根据ID和MD5获取特定数据集的信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取数据集信息",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) }),
            @ApiResponse(responseCode = "404", description = "数据集未找到",
                    content = @Content)
    })
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getDatasetByIdAndMd5(@RequestParam Long id, @RequestParam String md5) {
        Optional<DatasetEntity> dataset = datasetService.getDatasetByIdAndMd5(id, md5);
        if (dataset.isPresent()) {
            DatasetVo datasetVo = DtoConverter.convertDatasetEntityToDatasetVo(dataset.get());

            // 获取当前用户角色的名称
            UserEntity currentUser = userService.getCurrentUser();
            Set<String> roleNames = currentUser.getRoles().stream()
                    .map(RoleEntity::getName)
                    .collect(Collectors.toSet());
            datasetVo.setUsagePermissions(roleNames);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", datasetVo);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "获取模型信息", description = "根据ID和MD5获取模型信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取模型信息",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "数据集未找到",
                    content = @Content)
    })
    @GetMapping("/ModelInfo")
    public ResponseEntity getModelInfo(@RequestParam Long id, @RequestParam String md5) {
        Optional<DatasetEntity> dataset = datasetService.getDatasetByIdAndMd5(id, md5);
        if (dataset.isPresent()) {
            String modelInfo = openaiService.getReplace(dataset.get().getDescription());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of("list", modelInfo));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dataset not found");
        }
    }

    // 获取数据集排行榜
    @Operation(summary = "获取数据集排行榜", description = "获取按下载次数排序的数据集排行榜")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取数据集排行榜",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) })
    })
    @GetMapping("/rankings")
    public ResponseEntity<Map<String, Object>> getDatasetRankings() {
        List<DatasetEntity> datasets = datasetService.getAllDatasets();
        datasets.sort((d1, d2) -> Integer.compare(d2.getDownloadCount(), d1.getDownloadCount())); // 按下载次数排序
        List<DatasetEntity> topDatasets = datasets.stream().limit(8).collect(Collectors.toList()); // 取前10个
        List<DatasetVo> datasetVOs = topDatasets.stream()
                .map(DtoConverter::convertDatasetEntityToDatasetVo)
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", Map.of("list", datasetVOs));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "获取最新数据集", description = "获取最新添加的数据集")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取最新数据集",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DatasetVo.class)) })
    })
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestDatasets() {
        List<DatasetEntity> datasets = datasetService.getLatestDatasets(); // 获取最新添加的10条数据集记录
        List<DatasetVo> datasetVOs = datasets.stream()
                .map(DtoConverter::convertDatasetEntityToDatasetVo)
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", Map.of("list", datasetVOs));
        return ResponseEntity.ok(response);
    }

//    @Operation(summary = "训练数据集模型", description = "根据数据集ID启动训练过程")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "训练已启动",
//                    content = { @Content(mediaType = "application/json") }),
//            @ApiResponse(responseCode = "404", description = "数据集未找到",
//                    content = @Content)
//    })
//    @PostMapping("/train/{id}")
//    public ResponseEntity<Map<String, Object>> trainDataset(@PathVariable Long id) {
//        Optional<DatasetEntity> datasetOptional = datasetService.getDatasetById(id);
//        if (datasetOptional.isPresent()) {
//            DatasetEntity dataset = datasetOptional.get();
//            datasetService.trainDataset(dataset);
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("message", "Training started for dataset: " + dataset.getName());
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success", false, "message", "Dataset not found"));
//        }
//    }

}