package com.employee.service;

import com.employee.entity.Employee;
import com.employee.entity.Owner;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void addEmployee() {
        Employee employee = new Employee();
        Owner owner = new Owner();

        when(ownerRepository.findById(1L))
                .thenReturn(Optional.of(owner));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.addEmployee(employee, 1L);

        assertSame(employee, result);
        assertSame(owner, employee.getOwner());
        verify(employeeRepository).save(employee);
    }

    @Test
    void addEmployeeOwnerNotFound() {
        when(ownerRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.addEmployee(new Employee(), 99L)
        );

        assertEquals("Owner with id 99 not found", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getEmployeesByOwner() {
        List<Employee> employees = List.of(new Employee(), new Employee());

        when(employeeRepository.findByOwnerOwnerId(1L))
                .thenReturn(employees);

        assertEquals(2, employeeService.getEmployeesByOwner(1L).size());
        verify(employeeRepository).findByOwnerOwnerId(1L);
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee();

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        assertSame(employee, employeeService.getEmployeeById(1));
    }

    @Test
    void getEmployeeByIdNotFound() {
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(99)
        );
    }

    @Test
    void updateEmployee() {
        Employee employee = new Employee();

        when(employeeRepository.save(employee)).thenReturn(employee);

        assertSame(employee, employeeService.updateEmployee(employee));
        verify(employeeRepository).save(employee);
    }

    @Test
    void deleteEmployee() {
        Employee employee = new Employee();

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1);

        verify(attendanceRepository).deleteByEmployeeEmployeeid(1);
        verify(employeeRepository).deleteById(1);
    }

    @Test
    void deleteEmployeeNotFound() {
        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(99)
        );

        verify(attendanceRepository, never())
                .deleteByEmployeeEmployeeid(anyInt());
        verify(employeeRepository, never()).deleteById(anyInt());
    }
}
