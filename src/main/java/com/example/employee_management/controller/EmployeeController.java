package com.example.employee_management.controller;

import com.example.employee_management.model.EmployeeEntity;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getEmployee(@RequestParam Map<String, String> params) {
        List<EmployeeEntity> employeeEntityList = employeeService.getEmployee(params);
        if (employeeEntityList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeEntityList);
    }

    @PostMapping
    public ResponseEntity<EmployeeEntity> createEmployee(@RequestBody Map<String, String> request) {
        EmployeeEntity employeeEntity = employeeService.createEmployee(request);
        URI location = URI.create("/employee/" + employeeEntity.getId());
        return ResponseEntity.created(location).body(employeeEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable Long id,
                                                         @RequestBody Map<String, String> request) {
        EmployeeEntity employeeEntity = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(employeeEntity);
    }
}
