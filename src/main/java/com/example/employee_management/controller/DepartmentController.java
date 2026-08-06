package com.example.employee_management.controller;

import com.example.employee_management.model.DepartmentEntity;
import com.example.employee_management.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentEntity>> getDepartment() {
        return ResponseEntity.ok(departmentService.getDepartment());
    }

    @PostMapping
    public ResponseEntity<DepartmentEntity> createDepartment(@RequestBody Map<String, String> request) {
        DepartmentEntity departmentEntity = departmentService.createDepartment(request.get("name"));
        URI location = URI.create("/department/" + departmentEntity.getId());
        return ResponseEntity.created(location).body(departmentEntity);
    }
}
