package com.localaihub.platform.service;

/**
 * @author jiang_star
 * @date 2024/3/26
 */
import com.localaihub.platform.model.Dataset;
import com.localaihub.platform.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    public List<Dataset> findAll() {
        return datasetRepository.findAll();
    }

    public Dataset save(Dataset dataset) {
        return datasetRepository.save(dataset);
    }

    // Add more service methods as needed
}