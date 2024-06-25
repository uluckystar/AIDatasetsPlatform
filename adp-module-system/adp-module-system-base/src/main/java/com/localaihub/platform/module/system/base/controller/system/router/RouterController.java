package com.localaihub.platform.module.system.base.controller.system.router;

import com.localaihub.platform.framework.common.result.router.Router;
import com.localaihub.platform.module.system.base.service.router.RouterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路由控制器，处理路由相关的请求。
 *
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/15 14:27
 */
@RestController
@RequestMapping("/app")
@Tag(name = "异步路由", description = "处理异步路由相关操作")
public class RouterController {

    @Autowired
    private RouterService routerService;

    /**
     * 获取异步路由列表。
     *
     * @return 包含成功标识和路由数据的响应对象。
     */
    @Operation(summary = "获取异步路由列表", description = "获取系统中所有的异步路由")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RouterResponse.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/get-async-routes")
    public Map<String, Object> getAsyncRoutes() {
        List<Router> routes = routerService.getAsyncRoutes();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", routes);
        return response;
    }

    /**
     * 用于API文档的路由响应类。
     */
    private static class RouterResponse {
        private boolean success;
        private List<Router> data;

    }
}
