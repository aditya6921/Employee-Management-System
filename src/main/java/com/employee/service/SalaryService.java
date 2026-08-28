package com.employee.service;

import com.employee.entity.Attendance;
import com.employee.entity.AttendanceStatus;
import com.employee.entity.Employee;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalaryService {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;

    public SalaryService(EmployeeRepository employeeRepository,
                         AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public Map<String, Object> calculateSalary(
            int employeeid,
            int year,
            int month,
            boolean paidLeave) {

        // Find employee
        Employee employee = employeeRepository.findById(employeeid)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Get first and last date of the month
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // Get attendance records for the employee
        List<Attendance> attendanceList =
                attendanceRepository.findByEmployeeEmployeeidAndDateBetween(
                        employeeid, startDate, endDate);

        // Salary details
        double monthlySalary = employee.getSalary();
        int daysInMonth = startDate.lengthOfMonth();

        // Daily salary
        double dailySalary = monthlySalary / daysInMonth;

        // Attendance counters
        int absentDays = 0;
        int halfDays = 0;
        int leaveDays = 0;

        // Count attendance statuses
        for (Attendance attendance : attendanceList) {

            if (attendance.getStatus() == AttendanceStatus.ABSENT) {
                absentDays++;
            }

            if (attendance.getStatus() == AttendanceStatus.HALF_DAY) {
                halfDays++;
            }

            if (attendance.getStatus() == AttendanceStatus.LEAVE) {
                leaveDays++;
            }
        }

        // Attendance deductions
        double absentDeduction = absentDays * dailySalary;

        double halfDayDeduction = halfDays * (dailySalary / 2);

        double leaveDeduction = 0;

        if (!paidLeave) {
            leaveDeduction = leaveDays * dailySalary;
        }

        double totalDeduction =
                absentDeduction + halfDayDeduction + leaveDeduction;

        // Salary after attendance deductions
        double normalPayableSalary =
                monthlySalary - totalDeduction;

        // -----------------------------------------
        // OVERTIME CALCULATION
        // -----------------------------------------

        // Total overtime hours for the month
        double totalOvertimeHours = 0;

        for (Attendance attendance : attendanceList) {
            totalOvertimeHours += attendance.getOvertimeHours();
        }

        // Normal working hours = 8 hours per day
        double hourlySalary = dailySalary / 8;

        // Overtime payment
        double overtimePay =
                totalOvertimeHours * hourlySalary;

        // Final salary including overtime
        double finalPayableSalary =
                normalPayableSalary + overtimePay;

        // -----------------------------------------
        // RESPONSE
        // -----------------------------------------

        Map<String, Object> result = new HashMap<>();

        result.put("employeeId", employeeid);
        result.put("employeeName", employee.getEmployeName());

        result.put("year", year);
        result.put("month", month);

        // Salary
        result.put("monthlySalary", monthlySalary);
        result.put("dailySalary", dailySalary);

        // Attendance
        result.put("absentDays", absentDays);
        result.put("halfDays", halfDays);
        result.put("leaveDays", leaveDays);
        result.put("paidLeave", paidLeave);

        // Deductions
        result.put("absentDeduction", absentDeduction);
        result.put("halfDayDeduction", halfDayDeduction);
        result.put("leaveDeduction", leaveDeduction);
        result.put("totalDeduction", totalDeduction);

        // Normal salary
        result.put("normalPayableSalary", normalPayableSalary);

        // Overtime
        result.put("totalOvertimeHours", totalOvertimeHours);
        result.put("hourlySalary", hourlySalary);
        result.put("overtimePay", overtimePay);

        // Final salary
        result.put("finalPayableSalary", finalPayableSalary);

        return result;
    }
}