package com.localaihub.platform.module.system.base.dao.user;

import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 11:31
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
