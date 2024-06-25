package com.localaihub.platform.module.system.base.service.user;

import com.localaihub.platform.module.system.base.dao.user.RoleRepository;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/7 19:25
 */
@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    public RoleRepository roleRepository;

    public Set<RoleEntity> getDefaultRoles() {
        // 从数据库获取默认角色，例如 "USER"
        RoleEntity defaultRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    // 如果不存在，创建并保存新角色
                    RoleEntity newRole = new RoleEntity();
                    newRole.setName("USER");
                    roleRepository.save(newRole);
                    return newRole;
                });
        return Collections.singleton(defaultRole);
    }

    @Override
    public List<RoleEntity> listRoleEntities() {
        return roleRepository.findAll();
    }

    public Page<RoleEntity> findAllByPage(String name, Integer status, Pageable pageable) {
        return roleRepository.findAllByParams(name, status, pageable);
    }

    public long getTotalRoles() {
        return roleRepository.count();
    }

    @Override
    public RoleEntity getOne(int id) {
        if (roleRepository.existsById(id)) {
            return roleRepository.getById(id);
        }
        return null;
    }

    @Override
    public RoleEntity add(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity delete(RoleEntity role) {
        return delete(role.getId());
    }

    @Override
    public RoleEntity delete(int id) {
        if (roleRepository.existsById(id)) {
            RoleEntity roleEntity = roleRepository.getById(id);
            roleRepository.deleteById(id);
            return roleEntity;
        }
        return null;
    }

    @Override
    public RoleEntity update(RoleEntity role) {
        if (roleRepository.existsById(role.getId())) {
            return roleRepository.save(role);
        }
        return null;
    }
    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }
    @Override
    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }
}
