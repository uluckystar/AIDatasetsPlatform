package com.localaihub.platform.module.system.base.dao.dataset;

import com.localaihub.platform.module.system.base.entity.dataset.DatasetEntity;
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
 * @date 2024/5/19 01:34
 */
@Repository
public interface DatasetRepository extends JpaRepository<DatasetEntity, Long> , JpaSpecificationExecutor<DatasetEntity> {

    @Query("SELECT COUNT(d) FROM DatasetEntity d")
    long countDatasets();

    @Query("SELECT COUNT(d) FROM DatasetEntity d WHERE d.createdAt >= :startDate AND d.createdAt < :endDate")
    long countNewDatasetsBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DATE(d.createdAt), COUNT(d) FROM DatasetEntity d WHERE d.createdAt >= :startDate GROUP BY DATE(d.createdAt)")
    List<Object[]> getDailyIncreases(@Param("startDate") LocalDateTime startDate);

    Optional<DatasetEntity> findByIdAndMd5(Long id, String md5);
    List<DatasetEntity> findByType(String type);
    List<DatasetEntity> findTop10ByOrderByCreatedAtDesc();
}