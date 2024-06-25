package com.localaihub.platform.module.system.base.dao.user;

import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 17:41
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>, JpaSpecificationExecutor<RoleEntity> {
    Optional<RoleEntity> findByName(String name);

    @Query("SELECT r FROM RoleEntity r WHERE (:name IS NULL OR r.name LIKE %:name%) AND (:status IS NULL OR r.status = :status)")
    Page<RoleEntity> findAllByParams(@Param("name") String name, @Param("status") Integer status, Pageable pageable);
}
