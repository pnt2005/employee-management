package com.example.employee_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
public class DepartmentEntity {
    @OneToMany(mappedBy = "employee")
    private List<EmployeeEntity> employeeEntityList = new ArrayList<>();
}
