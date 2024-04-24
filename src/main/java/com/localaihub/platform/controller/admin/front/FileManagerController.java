package com.localaihub.platform.controller.admin.front;

/**
 * @author jiang_star
 * @date 2024/3/27
 */

import com.localaihub.platform.model.Dataset;
import com.localaihub.platform.service.DatasetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FileManagerController {
    private final DatasetService datasetService;

    public FileManagerController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

        @GetMapping("/file-manager")
    public String fileManager(Model model) {
        // 返回的是templates下的FileManager.html文件名（不包括.html扩展名）
        List<Dataset> datasets = datasetService.findAll();
        // Add datasets to the model to be accessible in the Thymeleaf template
        model.addAttribute("files", datasets);
        return "FileManager";
    }
}

