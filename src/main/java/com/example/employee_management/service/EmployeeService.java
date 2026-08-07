package com.example.employee_management.service;

import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.DepartmentEntity;
import com.example.employee_management.model.EmployeeEntity;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeEntity> getEmployee(Map<String, String> params) {
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        String name = params.getOrDefault("name", "");
        String department = params.getOrDefault("department", "");
        if (!name.isEmpty()) {
            employeeEntityList = employeeRepository.findAllByName(name);
        }
        else if (!department.isEmpty()) {
            employeeEntityList = employeeRepository.findAllByDepartmentName(department);
        }
        else {
            employeeEntityList = employeeRepository.findAll();
        }
        return employeeEntityList;
    }

    public EmployeeEntity createEmployee(Map<String, String> request) {
        logger.info("Adding new employee: name={}, email={}, department={}", request.get("name"), request.get("email"), request.get("department"));
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(request.get("email"));
        employeeEntity.setName(request.get("name"));

        DepartmentEntity departmentEntity = departmentRepository.getByName(request.get("department"));
        employeeEntity.setDepartment(departmentEntity);
        departmentEntity.getEmployeeEntityList().add(employeeEntity);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        logger.info("Employee created successfully with id: {}", savedEmployee.getId());
        return savedEmployee;
    }

    public void deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with id: {}", id);
        if (!employeeRepository.existsById(id)) {
            logger.warn("Employee with id {} not found for deletion", id);
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        employeeRepository.deleteById(id);
        logger.info("Employee with id {} deleted successfully", id);
    }

    public EmployeeEntity updateEmployee(Long id, Map<String, String> request) {
        logger.info("Attempting to update employee with id: {}", id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee with id {} not found for update", id);
                    return new EmployeeNotFoundException("Employee with id " + id + " not found");
                });
        employeeEntity.setName(request.get("name"));
        employeeEntity.setEmail(request.get("email"));
        employeeEntity.setDepartment(departmentRepository.getByName(request.get("department")));
        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        logger.info("Employee with id {} updated successfully", updatedEmployee.getId());
        return updatedEmployee;
    }

    @Cacheable("employee")
    public Integer getEmployeeQuantity() {
        return employeeRepository.findAll().size();
    }
}
