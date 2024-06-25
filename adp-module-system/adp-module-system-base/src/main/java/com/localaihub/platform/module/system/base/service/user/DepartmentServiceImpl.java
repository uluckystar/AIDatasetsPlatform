package com.localaihub.platform.module.system.base.service.user;

import com.localaihub.platform.module.system.base.dao.user.DepartmentRepository;
import com.localaihub.platform.module.system.base.entity.user.DepartmentEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 11:31
 */
@Service("DepartmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentEntity createOrUpdateDepartment(DepartmentEntity department) {
        // 检查传入的部门是否有ID
        if (department.getId() != null) {
            // 根据ID查找现有部门
            Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(department.getId());
            if (existingDepartment.isPresent()) {
                // 如果找到现有部门，更新部门信息
                DepartmentEntity updatedDepartment = existingDepartment.get();
                updatedDepartment.setName(department.getName());
                updatedDepartment.setParentId(department.getParentId());
                updatedDepartment.setPhone(department.getPhone());
                updatedDepartment.setPrincipal(department.getPrincipal());
                updatedDepartment.setRemark(department.getRemark());
                updatedDepartment.setSort(department.getSort());
                updatedDepartment.setStatus(department.getStatus());
                updatedDepartment.setEmail(department.getEmail());
                return departmentRepository.save(updatedDepartment);
            }
        }
        // 如果没有找到现有部门或没有ID，创建新部门
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        ZonedDateTime adjustedTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC+0"));
        LocalDateTime adjustedLocalDateTime = adjustedTime.toLocalDateTime();
        department.setCreateTime(adjustedLocalDateTime);
        return departmentRepository.save(department);
    }

    public Optional<DepartmentEntity> findById(Integer id) {
        return departmentRepository.findById(id);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

    public List<DepartmentEntity> findAll() {
        return departmentRepository.findAll();
    }
}
