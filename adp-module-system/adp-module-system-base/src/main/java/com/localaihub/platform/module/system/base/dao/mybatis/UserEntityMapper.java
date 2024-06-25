package com.localaihub.platform.module.system.base.dao.mybatis;

import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/6/25 19:00
 */
@Mapper
public interface UserEntityMapper {
    @Select("SELECT * FROM user")
    List<UserEntity> findAll();
}
