package com.localaihub.platform.module.system.base.dao.user;

import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/2 17:38
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    @Query("SELECT COUNT(u) FROM UserEntity u")
    long countUsers();

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.createTime >= :startDate AND u.createTime < :endDate")
    long countNewUsersBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DATE(u.createTime), COUNT(u) FROM UserEntity u WHERE u.createTime >= :startDate GROUP BY DATE(u.createTime)")
    List<Object[]> getDailyIncreases(@Param("startDate") LocalDateTime startDate);


    @Query("SELECT u FROM UserEntity u WHERE "
            + "(:deptId IS NULL OR u.dept.id = :deptId) AND "
            + "(:username IS NULL OR u.username LIKE %:username%) AND "
            + "(:phone IS NULL OR u.phone LIKE %:phone%) AND "
            + "(:status IS NULL OR u.status = :status)")
    Page<UserEntity> findAllByParams(Integer deptId, String username, String phone, Integer status, Pageable pageable);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);
}
