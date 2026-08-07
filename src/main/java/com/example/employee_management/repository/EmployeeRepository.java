package com.example.employee_management.repository;

import com.example.employee_management.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByName(String name);

    List<EmployeeEntity> findAllByDepartmentName(String department);
}
