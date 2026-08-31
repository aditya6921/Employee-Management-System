package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://employee-management-system-frontend-plum.vercel.app")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get employees of a specific owner
    @GetMapping
    public List<Employee> getEmployeesByOwner(
            @RequestParam Long ownerId) {

        return employeeService.getEmployeesByOwner(ownerId);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {

        return employeeService.getEmployeeById(id);
    }

    // Add employee under a specific owner
    @PostMapping
    public Employee addEmployee(
            @RequestParam Long ownerId,
            @RequestBody Employee employee) {

        return employeeService.addEmployee(employee, ownerId);
    }

    // Update employee
    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {

        return employeeService.updateEmployee(employee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {

        employeeService.deleteEmployee(id);

        return "Employee deleted successfully";
    }
}