package com.localaihub.platform.module.system.base.service.user;

import com.localaihub.platform.module.system.base.dto.UserDto;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:33
 */
public interface UserService {

    long countUsers();

    List<Integer> getDailyIncreases(int days);

    UserDetailsService userDetailsService();

    void registerNewUser(UserEntity user);

    String encodePassword(String password);

    List<UserEntity> findAll();

    Page<UserDto> findAllByPage(Integer deptId, String username, String phone, Integer status, Pageable pageable);

    public UserEntity updateUser(UserEntity user);

    UserEntity updateUserStatus(UserEntity user);

    long getTotalUsers();

    UserEntity getOne(int id);

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    UserEntity saveUser(UserDto userDto);

    UserEntity save(UserEntity userEntity);

    UserEntity delete(UserEntity user);

    UserEntity delete(int id);

    UserEntity update(UserEntity user);

    /**
     * 根据用户的token查询当前用户
     * 如果返回为空则没有用户使用的token
     *
     * @param token 用户token
     * @return 用户信息；空
     */
    UserEntity getUserByToken(String token);

    UserEntity getCurrentUser();
}