package com.localaihub.platform.module.system.base.controller.dataset;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import com.localaihub.platform.module.system.base.service.dataset.DatasetService;
import com.localaihub.platform.module.system.base.service.dataset.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/19 01:47
 */
@RestController
@RequestMapping("/app/files")
@Tag(name = "数据集文件管理", description = "提供文件上传、下载、预览和删除的API接口")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private DatasetService datasetService;

    @Value("${spring.web.resources.static-locations}")
    private String staticLocations;

    @Operation(summary = "上传文件", description = "上传文件到指定的数据集")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "文件上传成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileEntity.class))),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "500", description = "文件上传失败")
    })
    @PostMapping("/upload")
    public ResponseEntity uploadFiles(@RequestParam("datasetId") Long datasetId,
                                                        @RequestParam("files") MultipartFile[] files,
                                                        @RequestParam("category") String category,
                                                        @RequestParam("tags") String tags,
                                                        @RequestParam("features") String features) {
        List<FileEntity> fileEntities = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> tagsList = objectMapper.readValue(tags, new TypeReference<List<String>>() {
            });
            List<String> featuresList = objectMapper.readValue(features, new TypeReference<List<String>>() {
            });
            Optional<DatasetEntity> datasetOptional = datasetService.getDatasetById(datasetId);
            if (datasetOptional.isPresent()) {
                DatasetEntity dataset = datasetOptional.get();
                Path datasetPath = Paths.get(dataset.getPath()).normalize();
                Files.createDirectories(datasetPath);

                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    Path targetLocation = datasetPath.resolve(fileName);
                    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                    if (fileName.endsWith(".zip")) {
                        // 解压zip文件
                        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(targetLocation))) {
                            ZipEntry entry;
                            while ((entry = zipInputStream.getNextEntry()) != null) {

                                String entryName = entry.getName();
                                if (entryName.startsWith("__MACOSX") || entryName.startsWith("._")) {
                                    continue; // 跳过 __MACOSX 文件夹和以 ._ 开头的文件
                                }

                                Path extractedFilePath = datasetPath.resolve(entry.getName()).normalize();
                                if (!extractedFilePath.startsWith(datasetPath)) {
                                    // 防止ZIP穿越攻击
                                    throw new IOException("ZIP entry is outside of the target dir: " + entryName);
                                }

                                if (entry.isDirectory()) {
                                    Files.createDirectories(extractedFilePath);
                                } else {
                                    Files.createDirectories(extractedFilePath.getParent());
                                    Files.copy(zipInputStream, extractedFilePath, StandardCopyOption.REPLACE_EXISTING);

                                    FileEntity newFile = fileService.saveFile(extractedFilePath, dataset, category, tagsList, featuresList);
                                    fileEntities.add(newFile);
                                }
                            }
                        }
                        Files.delete(targetLocation); // 删除zip文件
                    } else {
                        FileEntity newFile = fileService.saveFile(file, dataset, category, tagsList, featuresList);
                        fileEntities.add(newFile);
                    }
                }

                // 更新数据集的大小
                long totalSize = dataset.getFileEntities().stream().mapToLong(FileEntity::getSize).sum();
                dataset.setDatasetSize(totalSize);
                datasetService.saveDataset(dataset);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", fileEntities);
                return ResponseEntity.ok(response);
//                return ResponseEntity.ok(fileEntities);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "下载文件", description = "根据文件ID下载文件")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功下载文件",
                    content = { @Content(mediaType = "application/octet-stream") }),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "500", description = "文件下载失败")
    })
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            Optional<FileEntity> fileOptional = fileService.getFileById(id);
            if (fileOptional.isPresent()) {
                FileEntity file = fileOptional.get();
                Path filePath = Paths.get(file.getPath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.status(500).build();
        }
    }


    @Operation(summary = "预览文件", description = "根据文件路径预览文件")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功预览文件",
                    content = { @Content(mediaType = "application/octet-stream") }),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "500", description = "文件预览失败")
    })
    @GetMapping("/preview/**")
    public ResponseEntity<Resource> previewFile(HttpServletRequest request) {
        try {
            // 获取并解码文件名
            String requestURI = request.getRequestURI();
            String encodedFilename = requestURI.substring("/app/files/preview/".length());
//            String encodedFilename = request.getRequestURI().split("/app/files/preview/")[1];
            String filename = URLDecoder.decode(encodedFilename, StandardCharsets.UTF_8.toString());

            // 生成文件路径
            Path filePath = Paths.get(staticLocations, filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查资源是否存在和可读
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 获取文件的媒体类型
            MediaType mediaType = getMediaTypeForFileName(filePath);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(500).build();
        }
    }

    private MediaType getMediaTypeForFileName(Path filePath) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(filePath);
        } catch (IOException e) {
            mimeType = "application/octet-stream"; // 默认类型
        }

        return MediaType.parseMediaType(mimeType);
    }

    @Operation(summary = "根据数据集ID获取文件列表", description = "获取指定数据集的所有文件列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取文件列表",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "数据集未找到"),
    })
    @GetMapping("/dataset/{datasetId}")
    public ResponseEntity<Map<String, Object>> getFilesByDatasetId(@PathVariable Long datasetId) {
        Map<String, Object> response = new HashMap<>();
        Optional<DatasetEntity> datasetOptional = datasetService.getDatasetById(datasetId);
        if (datasetOptional.isPresent()) {
            List<FileEntity> files = fileService.getFilesByDataset(datasetOptional.get());
            response.put("success", true);
            response.put("data", files);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("data", null);
            return ResponseEntity.ok(response);
        }
    }

    @Operation(summary = "删除文件", description = "根据文件ID删除文件")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功删除文件",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "500", description = "文件删除失败")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<FileEntity> fileOptional = fileService.getFileById(id);
            if (fileOptional.isPresent()) {
                FileEntity file = fileOptional.get();
                Path filePath = Paths.get(file.getPath()).normalize();
                Files.deleteIfExists(filePath);

                fileService.deleteFile(id);

                response.put("success", true);
                response.put("message", "File deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "File not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (IOException ex) {
            response.put("success", false);
            response.put("message", "Error deleting file");
            return ResponseEntity.status(500).body(response);
        }
    }


}