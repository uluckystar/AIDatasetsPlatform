package com.localaihub.platform.module.system.base.controller.admin;

import com.localaihub.platform.framework.common.constants.ResultCode;
import com.localaihub.platform.framework.common.constants.ResultMessage;
import com.localaihub.platform.framework.common.result.Result;
import com.localaihub.platform.framework.common.result.ResultData;
import com.localaihub.platform.framework.common.result.ResultTable;
import com.localaihub.platform.module.system.base.dto.RoleDto;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.service.user.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/7 15:57
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/role")
@Tag(name = "角色管理", description = "RoleController")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping(value = "/paging")
    @Operation(summary = "分页查询角色", description = "分页查询角色")
    public ResultTable getAllRoles(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "ststus", required = false) Integer ststus,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage) {
        // 创建分页对象
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize); // Spring Data JPA 从0开始计数，所以要减1
        Page<RoleEntity> rolePage = roleService.findAllByPage(name, ststus, pageable);
        List<RoleEntity> roles = rolePage.getContent();

        // 将RoleEntity转换为RoleDto
        List<RoleDto> roleDtos = roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDto.setStatus(role.getStatus());
            return roleDto;
        }).collect(Collectors.toList());

        long total = roleService.getTotalRoles();
        return new ResultTable<>(true, new ResultData<>(roleDtos,total,pageSize,currentPage));
    }

    @GetMapping
    @Operation(summary = "获取角色列表", description = "获取所有角色的列表")
    public Result getAllRoles() {
        return new Result(
                ResultCode.OK,
                ResultMessage.OK,
                roleService.listRoleEntities()
        );
    }
}
