package com.localaihub.platform.module.system.base.controller.admin;

import com.localaihub.platform.framework.common.constants.ResultCode;
import com.localaihub.platform.framework.common.constants.ResultMessage;
import com.localaihub.platform.framework.common.result.*;
import com.localaihub.platform.module.system.base.convert.DtoConverter;
import com.localaihub.platform.module.system.base.dao.mybatis.UserEntityMapper;
import com.localaihub.platform.module.system.base.dto.UserDto;
import com.localaihub.platform.module.system.base.dto.UserStatusDto;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.jwt.IAuthenticationFacade;
import com.localaihub.platform.module.system.base.service.user.UserService;
import com.localaihub.platform.module.system.base.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:30
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/user")
@Tag(name = "用户管理", description = "UserController")
public class UserController {

    @Autowired
    private UserService userService; // 用户服务
    @Autowired
    private IAuthenticationFacade authenticationFacade; // 认证服务
    @Autowired
    private UserEntityMapper userEntityMapper;

    @GetMapping
    @Operation(summary = "分页查询用户", description = "分页查询用户")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResultTable getAllUsers(@RequestParam(value = "deptId", required = false) Integer deptId,
                                   @RequestParam(value = "username", required = false) String username,
                                   @RequestParam(value = "phone", required = false) String phone,
                                   @RequestParam(value = "status", required = false) Integer status,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("currentPage") Integer currentPage) {
        // 创建分页对象
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize); // Spring Data JPA 从0开始计数，所以要减1
        Page<UserDto> userPage = userService.findAllByPage(deptId, username, phone, status,pageable);
        List<UserVo> userVos = userPage.stream()
                .map(DtoConverter::convertUserDtoToUserVo)
                .collect(Collectors.toList());
        long total = userService.getTotalUsers();
        return new ResultTable<>(true, new ResultData<>(userVos, total, pageSize, currentPage));
    }

    @GetMapping("/mybatis")
    public Result getAllUsersByMybatis() {
        List<UserEntity> users = userEntityMapper.findAll();
        List<UserVo> userVos = users.stream()
                .map(DtoConverter::convertUserEntityToUserVo)
                .collect(Collectors.toList());
        return new Result<>(200, "Success", userVos);
    }

    @PostMapping
    @Operation(summary = "添加用户", description = "添加用户")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResultBaseList addUser(@RequestBody UserDto userDto) {
        List<UserEntity> user = new ArrayList<>();
        user.add(userService.saveUser(userDto));
        return new ResultBaseList<>(true, user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新用户信息", description = "根据提供的用户信息更新用户")
    @ApiResponse(responseCode = "200", description = "用户信息更新成功",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class))})
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResultBase updateUser(@RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return new ResultBase(true, updatedUser);
        } else {
            return new ResultBase(false, updatedUser);
        }
    }

    @GetMapping(value = "/info")
    @Operation(summary = "获取用户信息", description = "获取用户信息")
//    @PreAuthorize("hasRole('USER')")
    public Result getUserInfo() {
        String username = authenticationFacade.getAuthentication().getName();
        UserEntity user = userService.findByUsername(username);
        return new Result(ResultCode.OK, ResultMessage.OK, user);
    }

    @PutMapping(value = "/status",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新用户状态", description = "根据提供的用户信息更新用户状态")
    @ApiResponse(responseCode = "200", description = "用户状态更新成功",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class))})
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResultBase updateUserStatus(@RequestBody UserStatusDto userStatusDto) {
        UserEntity userEntity = DtoConverter.convertUserStatusDtoToUserEntity(userStatusDto);
        UserEntity updatedUser = userService.updateUserStatus(userEntity);
        if (updatedUser != null) {
            return new ResultBase(true, updatedUser);
        } else {
            return new ResultBase(false, updatedUser);
        }
    }
}
