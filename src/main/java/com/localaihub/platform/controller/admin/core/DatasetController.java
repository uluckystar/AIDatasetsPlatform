package com.localaihub.platform.controller.admin.core;

/**
 * @author jiang_star
 * @date 2024/3/26
 */
import com.localaihub.platform.model.Dataset;
import com.localaihub.platform.service.DatasetService;
import com.localaihub.platform.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/admin-api/datasets")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping
    public ResponseEntity<List<Dataset>> getAllDatasets() {
        List<Dataset> datasets = datasetService.findAll();
        return ResponseEntity.ok(datasets);
    }

    @PostMapping
    public ResponseEntity<Dataset> createDataset(@RequestBody Dataset dataset) {
        Dataset savedDataset = datasetService.save(dataset);
        return ResponseEntity.ok(savedDataset);
    }

    // Implement more endpoints as needed
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            System.out.println(fileName);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/" + fileName)
                    .toUriString();
            System.out.println(fileDownloadUri);
            return ResponseEntity.ok(fileName + "\n新文件路径：" + fileDownloadUri);
        } catch (FileAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法存储文件：" + file.getOriginalFilename());
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = null;
        try {
            resource = fileStorageService.loadFileAsResource(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Log error and proceed with a default content type
        }

        if (contentType == null) {
            contentType = "application/octet-stream"; // Fallback
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}