package com.localaihub.platform.module.system.base.service.user;

import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/7 19:22
 */
public interface RoleService {

    Set<RoleEntity> getDefaultRoles();

    List<RoleEntity> listRoleEntities();

    Page<RoleEntity> findAllByPage(String name, Integer ststus, Pageable pageable);

    long getTotalRoles();

    RoleEntity getOne(int id);

    RoleEntity add(RoleEntity role);

    RoleEntity delete(RoleEntity role);

    RoleEntity delete(int id);

    RoleEntity update(RoleEntity role);

    public Optional<RoleEntity> findByName(String name);

    public RoleEntity save(RoleEntity role);

}
