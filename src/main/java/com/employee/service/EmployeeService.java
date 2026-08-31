package com.employee.service;

import com.employee.entity.Employee;
import com.employee.entity.Owner;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final OwnerRepository ownerRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            AttendanceRepository attendanceRepository,
            OwnerRepository ownerRepository) {

        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.ownerRepository = ownerRepository;
    }

    // Add employee under a specific owner
    public Employee addEmployee(Employee employee, Long ownerId) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Owner with id " + ownerId + " not found"
                        )
                );

        employee.setOwner(owner);

        return employeeRepository.save(employee);
    }

    // Get all employees of a specific owner
    public List<Employee> getEmployeesByOwner(Long ownerId) {

        return employeeRepository.findByOwnerOwnerId(ownerId);
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with id " + id + " not found"
                        )
                );
    }

    // Update employee
    public Employee updateEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    // Delete employee
    @Transactional
    public void deleteEmployee(int id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with id " + id + " not found"
                        )
                );

        // First delete attendance records
        // belonging to this employee
        attendanceRepository.deleteByEmployeeEmployeeid(id);

        // Then delete employee
        employeeRepository.deleteById(id);
    }
}