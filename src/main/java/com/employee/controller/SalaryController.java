package com.employee.controller;

import com.employee.service.SalaryService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/{employeeid}/{year}/{month}")
    public Map<String, Object> calculateSalary(
            @PathVariable int employeeid,
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam boolean paidLeave) {

        return salaryService.calculateSalary(
                employeeid,
                year,
                month,
                paidLeave
        );
    }
}