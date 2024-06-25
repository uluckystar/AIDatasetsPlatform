package com.localaihub.platform.module.system.base.service.user;

import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;

import java.util.List;
import java.util.Optional;


/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 11:31
 */
public interface DepartmentService {
    public DepartmentEntity createOrUpdateDepartment(DepartmentEntity department);

    public Optional<DepartmentEntity> findById(Integer id);

    public void deleteDepartment(Integer id);

    public List<DepartmentEntity> findAll();
}
