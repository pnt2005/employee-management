package com.example.employee_management.controller;

import com.example.employee_management.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {
    
    private final EmployeeService employeeService;

    public EmployeeWebController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(@RequestParam(required = false) Map<String, String> params, Model model) {
        if (params == null) {
            params = new HashMap<>();
        }
        model.addAttribute("employees", employeeService.getEmployee(params));
        model.addAttribute("searchName", params.getOrDefault("name", ""));
        model.addAttribute("searchDepartment", params.getOrDefault("department", ""));
        return "employee-list";
    }

    @GetMapping("/add")
    public String addEmployeeForm() {
        return "employee-add";
    }

    @PostMapping("/add")
    public String addEmployeeSubmit(@RequestParam Map<String, String> params) {
        employeeService.createEmployee(params);
        return "redirect:/employees/list";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        model.addAttribute("totalQuantity", employeeService.getEmployeeQuantity());
        model.addAttribute("quantityByDepartment", employeeService.getEmployeeQuantityByDepartment(""));
        return "employee-statistics";
    }
}
