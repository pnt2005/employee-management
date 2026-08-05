package com.example.employee_management.repository;

import com.example.employee_management.model.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity getByName(String department);
}
