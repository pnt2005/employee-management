package com.example.employee_management.service;

import com.example.employee_management.model.DepartmentEntity;
import com.example.employee_management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentEntity> getDepartment() {
        return departmentRepository.findAll();
    }

    public DepartmentEntity createDepartment(String name) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setName(name);
        return departmentRepository.save(departmentEntity);
    }
}
