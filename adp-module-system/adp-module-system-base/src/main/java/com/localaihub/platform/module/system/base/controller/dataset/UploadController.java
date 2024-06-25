//package com.localaihub.platform.module.system.base.controller.dataset;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Jiaxing Jiang
// * @version 0.1.0-SNAPSHOT
// * @date 2024/5/18 00:06
// */
//@RestController
//@RequestMapping("/app/upload")
//@Tag(name = "数据集文件上传", description = "FileController")
//public class UploadController {
//
//    @PostMapping
//    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
//        Map<String, Object> response = new HashMap<>();
//        if (file.isEmpty()) {
//            response.put("success", false);
//            response.put("message", "文件不能为空");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // 文件保存路径，可以根据需要修改
//            String filePath = "/path/to/save/" + file.getOriginalFilename();
//            File dest = new File(filePath);
//            file.transferTo(dest);
//
//            response.put("success", true);
//            response.put("message", "文件上传成功");
//            response.put("fileName", file.getOriginalFilename());
//            response.put("filePath", filePath);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (IOException e) {
//            response.put("success", false);
//            response.put("message", "文件上传失败: " + e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/multiple")
//    public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
//        Map<String, Object> response = new HashMap<>();
//        List<String> fileNames = new ArrayList<>();
//
//        if (files.length == 0) {
//            response.put("success", false);
//            response.put("message", "文件不能为空");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        for (MultipartFile file : files) {
//            try {
//                String filePath = "/path/to/save/" + file.getOriginalFilename();
//                File dest = new File(filePath);
//                file.transferTo(dest);
//                fileNames.add(file.getOriginalFilename());
//            } catch (IOException e) {
//                response.put("success", false);
//                response.put("message", "文件上传失败: " + e.getMessage());
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        response.put("success", true);
//        response.put("message", "文件上传成功");
//        response.put("fileNames", fileNames);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}