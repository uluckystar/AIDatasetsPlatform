package com.localaihub.platform.module.system.base.controller.admin;

import com.localaihub.platform.framework.common.result.ResultBase;
import com.localaihub.platform.framework.common.result.ResultBaseList;
import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;
import com.localaihub.platform.module.system.base.service.user.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 11:36
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/dept")
@Tag(name = "团队管理", description = "DepartmentController")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "创建新部门", description = "在数据库中添加新的部门记录。")
    @ApiResponse(responseCode = "200", description = "部门已创建",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentEntity.class)) })
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody DepartmentEntity department) {
        return ResponseEntity.ok(departmentService.createOrUpdateDepartment(department));
    }

    @PutMapping
    @Operation(summary = "更新部门", description = "更新现有部门的信息。")
    @ApiResponse(responseCode = "200", description = "部门已更新",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentEntity.class)) })
    public ResponseEntity<DepartmentEntity> updateDepartment(@RequestBody DepartmentEntity department) {
        return ResponseEntity.ok(departmentService.createOrUpdateDepartment(department));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门", description = "通过部门ID删除部门。")
    @ApiResponse(responseCode = "200", description = "部门已删除")
    public ResultBase deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return new ResultBase(true,"");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取部门信息", description = "通过部门ID获取部门详细信息。")
    @ApiResponse(responseCode = "200", description = "部门信息获取成功",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentEntity.class)) })
    public ResponseEntity<DepartmentEntity> getDepartment(@PathVariable Integer id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "列出所有部门", description = "获取所有部门的列表。")
    @ApiResponse(responseCode = "200", description = "部门列表获取成功",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentEntity.class)) })
    public ResultBaseList getAllDepartments() {
        List<DepartmentEntity> departments = departmentService.findAll();
        return new ResultBaseList<>(true, departments);
    }
}
