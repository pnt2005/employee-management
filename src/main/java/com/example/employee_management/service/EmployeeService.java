package com.example.employee_management.service;

import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.EmployeeEntity;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeEntity> getEmployee(Map<String, String> params) {
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        String name = params.getOrDefault("name", null);
        String department = params.getOrDefault("department", null);
        if (!name.isEmpty()) {
            employeeEntityList = employeeRepository.findAllByName(name);
        }
        else if (!department.isEmpty()) {
            employeeEntityList = employeeRepository.findAllByDepartment(department);
        }
        else {
            employeeEntityList = employeeRepository.findAll();
        }
        return employeeEntityList;
    }

    public EmployeeEntity createEmployee(Map<String, String> request) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(request.get("email"));
        employeeEntity.setName(request.get("name"));
        employeeEntity.setDepartmentEntity(departmentRepository.getByName(request.get("department")));
        return employeeRepository.save(employeeEntity);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeEntity updateEmployee(Long id, Map<String, String> request) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        employeeEntity.setName(request.get("name"));
        employeeEntity.setEmail(request.get("email"));
        employeeEntity.setDepartmentEntity(departmentRepository.getByName(request.get("department")));
        return employeeRepository.save(employeeEntity);
    }
}
