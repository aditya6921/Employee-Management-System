
package com.employee.service;

import com.employee.entity.Attendance;
import com.employee.entity.AttendanceStatus;
import com.employee.entity.Employee;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private Employee employee;

    @InjectMocks
    private SalaryService salaryService;

    @BeforeEach
    void setUp() {
        when(employee.getSalary()).thenReturn(3100.0);
        when(employee.getEmployeName()).thenReturn("Rahul");
    }

    @Test
    void calculateSalary_shouldCalculateDeductionsAndOvertime() {

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        Attendance absent = mock(Attendance.class);
        when(absent.getStatus()).thenReturn(AttendanceStatus.ABSENT);
        when(absent.getOvertimeHours()).thenReturn(0.0);

        Attendance halfDay = mock(Attendance.class);
        when(halfDay.getStatus()).thenReturn(AttendanceStatus.HALF_DAY);
        when(halfDay.getOvertimeHours()).thenReturn(2.0);

        Attendance leave = mock(Attendance.class);
        when(leave.getStatus()).thenReturn(AttendanceStatus.LEAVE);
        when(leave.getOvertimeHours()).thenReturn(2.0);

        when(attendanceRepository
                .findByEmployeeEmployeeidAndDateBetween(
                        1,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 31)))
                .thenReturn(List.of(absent, halfDay, leave));

        Map<String, Object> result =
                salaryService.calculateSalary(1, 2026, 1, false);

        assertEquals(1, result.get("employeeId"));
        assertEquals("Rahul", result.get("employeeName"));

        assertEquals(3100.0, (double) result.get("monthlySalary"), 0.001);
        assertEquals(100.0, (double) result.get("dailySalary"), 0.001);

        assertEquals(1, result.get("absentDays"));
        assertEquals(1, result.get("halfDays"));
        assertEquals(1, result.get("leaveDays"));

        assertEquals(100.0, (double) result.get("absentDeduction"), 0.001);
        assertEquals(50.0, (double) result.get("halfDayDeduction"), 0.001);
        assertEquals(100.0, (double) result.get("leaveDeduction"), 0.001);

        assertEquals(250.0, (double) result.get("totalDeduction"), 0.001);
        assertEquals(2850.0, (double) result.get("normalPayableSalary"), 0.001);

        assertEquals(4.0, (double) result.get("totalOvertimeHours"), 0.001);
        assertEquals(12.5, (double) result.get("hourlySalary"), 0.001);
        assertEquals(50.0, (double) result.get("overtimePay"), 0.001);

        assertEquals(2900.0, (double) result.get("finalPayableSalary"), 0.001);

        verify(employeeRepository).findById(1);
        verify(attendanceRepository)
                .findByEmployeeEmployeeidAndDateBetween(
                        1,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 31));
    }

    @Test
    void calculateSalary_shouldNotDeductPaidLeave() {

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        Attendance leave = mock(Attendance.class);
        when(leave.getStatus()).thenReturn(AttendanceStatus.LEAVE);
        when(leave.getOvertimeHours()).thenReturn(0.0);

        when(attendanceRepository
                .findByEmployeeEmployeeidAndDateBetween(
                        1,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 31)))
                .thenReturn(List.of(leave));

        Map<String, Object> result =
                salaryService.calculateSalary(1, 2026, 1, true);

        assertEquals(1, result.get("leaveDays"));
        assertEquals(0.0, (double) result.get("leaveDeduction"), 0.001);
        assertEquals(0.0, (double) result.get("totalDeduction"), 0.001);
        assertEquals(3100.0, (double) result.get("normalPayableSalary"), 0.001);
        assertEquals(3100.0, (double) result.get("finalPayableSalary"), 0.001);
    }

    @Test
    void calculateSalary_shouldReturnFullSalaryWhenAttendanceIsEmpty() {

        when(employeeRepository.findById(1))
                .thenReturn(Optional.of(employee));

        when(attendanceRepository
                .findByEmployeeEmployeeidAndDateBetween(
                        1,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 31)))
                .thenReturn(List.of());

        Map<String, Object> result =
                salaryService.calculateSalary(1, 2026, 1, false);

        assertEquals(0, result.get("absentDays"));
        assertEquals(0, result.get("halfDays"));
        assertEquals(0, result.get("leaveDays"));

        assertEquals(0.0, (double) result.get("totalDeduction"), 0.001);
        assertEquals(0.0, (double) result.get("overtimePay"), 0.001);
        assertEquals(3100.0, (double) result.get("finalPayableSalary"), 0.001);
    }

    @Test
    void calculateSalary_shouldThrowExceptionWhenEmployeeDoesNotExist() {

        when(employeeRepository.findById(99))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> salaryService.calculateSalary(99, 2026, 1, false)
        );

        assertEquals("Employee not found", exception.getMessage());

        verifyNoInteractions(attendanceRepository);
    }
}
