package com.localaihub.platform.module.system.base.controller.dataset;

import com.localaihub.platform.module.system.base.service.dataset.DatasetService;
import com.localaihub.platform.module.system.base.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/21 01:53
 */
@RestController
@RequestMapping("/app/stats")
@Tag(name = "统计数据管理", description = "提供统计数据的查询接口")
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private DatasetService datasetService;

    @Operation(summary = "获取概览统计数据", description = "返回用户和数据集的总数、增长率以及最近7天的日增长数据")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取统计数据",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)) })
    })
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverviewStats() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        long totalUsers = userService.countUsers();
        long totalDatasets = datasetService.countDatasets();

        List<Integer> userDailyIncreases = userService.getDailyIncreases(7);
        List<Integer> datasetDailyIncreases = datasetService.getDailyIncreases(7);

        double userGrowth = 0;
        double datasetGrowth = 0;

        // 检查列表大小，确保有足够的元素进行计算
        if (userDailyIncreases.size() >= 2) {
            userGrowth = ((double) userDailyIncreases.get(userDailyIncreases.size() - 1) / (double) userDailyIncreases.get(userDailyIncreases.size() - 2)) * 100;
        }
        if (datasetDailyIncreases.size() >= 2) {
            datasetGrowth = ((double) datasetDailyIncreases.get(datasetDailyIncreases.size() - 1) / (double) datasetDailyIncreases.get(datasetDailyIncreases.size() - 2)) * 100;
        }

        data.put("totalUsers", totalUsers);
        data.put("userGrowth", String.format("+%.0f%%", userGrowth));
        data.put("userDailyIncreases", userDailyIncreases);
        data.put("totalDatasets", totalDatasets);
        data.put("datasetGrowth", String.format("+%.0f%%", datasetGrowth));
        data.put("datasetDailyIncreases", datasetDailyIncreases);

        response.put("success", true);
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
}