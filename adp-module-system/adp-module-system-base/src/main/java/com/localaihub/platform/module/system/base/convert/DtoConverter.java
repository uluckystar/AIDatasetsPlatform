package com.localaihub.platform.module.system.base.convert;

import com.localaihub.platform.module.system.base.dto.RoleDto;
import com.localaihub.platform.module.system.base.dto.UserDto;
import com.localaihub.platform.module.system.base.dto.UserStatusDto;
import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
import com.localaihub.platform.module.system.base.entity.dataset.FileEntity;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.vo.DatasetVo;
import com.localaihub.platform.module.system.base.vo.FileVo;
import com.localaihub.platform.module.system.base.vo.UserVo;
import org.springframework.beans.BeanUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/16 11:39
 */

public class DtoConverter {

    // DTO conversion methods
    public static UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        UserDto userDto = BeanConverter.convertTo(userEntity, UserDto.class);
        Set<RoleDto> roleDtos = BeanConverter.convertToSet(userEntity.getRoles(), RoleDto.class);
        userDto.setRoles(roleDtos);
        return userDto;
    }
    // Convert UserDto to UserEntity
    public static UserEntity convertUserStatusDtoToUserEntity(UserStatusDto userStatusDto) {
        UserEntity userEntity = BeanConverter.convertTo(userStatusDto, UserEntity.class);
        return userEntity;
    }

    public static UserVo convertUserEntityToUserVo(UserEntity userEntity) {
        return BeanConverter.convertTo(userEntity, UserVo.class);
    }
    public static UserVo convertUserDtoToUserVo(UserDto userDto) {
        return BeanConverter.convertTo(userDto, UserVo.class);
    }
    public static UserDto convertUserVoToUserDto(UserVo userVo) {
        return BeanConverter.convertTo(userVo, UserDto.class);
    }
    public static RoleDto convertRoleEntityToRoleDto(RoleEntity roleEntity) {
        return BeanConverter.convertTo(roleEntity, RoleDto.class);
    }

    public static RoleEntity convertRoleDtoToRoleEntity(RoleDto roleDto) {
        return BeanConverter.convertTo(roleDto, RoleEntity.class);
    }

    public static DatasetVo convertDatasetEntityToDatasetVo(DatasetEntity datasetEntity) {
        DatasetVo datasetVo = new DatasetVo();
        BeanUtils.copyProperties(datasetEntity, datasetVo);
        datasetVo.setOwner(datasetEntity.getOwner().getUsername());

        List<FileVo> fileVos = datasetEntity.getFileEntities().stream()
                .map(DtoConverter::convertFileEntityToFileVo)
                .collect(Collectors.toList());
        datasetVo.setFileEntities(fileVos);

        datasetVo.setDatasetSize(formatFileSize(datasetEntity.getDatasetSize()));
        // 获取用户的所有权限
        Set<String> roles = datasetEntity.getOwner().getRoles().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
        datasetVo.setUsagePermissions(roles);

        return datasetVo;
    }

    public static FileVo convertFileEntityToFileVo(FileEntity fileEntity) {
        FileVo fileVo = new FileVo();
        BeanUtils.copyProperties(fileEntity, fileVo);
        return fileVo;
    }

    private static String formatFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}