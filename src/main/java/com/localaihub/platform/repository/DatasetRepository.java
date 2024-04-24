package com.localaihub.platform.repository;

/**
 * @author jiang_star
 * @date 2024/3/26
 */
import com.localaihub.platform.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Long> {
}