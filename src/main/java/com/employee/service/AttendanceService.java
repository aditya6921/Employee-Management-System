package com.employee.service;

import com.employee.entity.Attendance;
import com.employee.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    // Get all attendance
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // Get attendance by ID
    public Attendance getAttendanceById(int id) {

        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attendance not found"));
    }

    // Add attendance
    public Attendance addAttendance(Attendance attendance) {

        calculateOvertime(attendance);

        return attendanceRepository.save(attendance);
    }

    // Update attendance
    public Attendance updateAttendance(Attendance attendance) {

        calculateOvertime(attendance);

        return attendanceRepository.save(attendance);
    }

    // Calculate overtime
    private void calculateOvertime(Attendance attendance) {

        if (attendance.getCheckIn() != null &&
                attendance.getCheckOut() != null) {

            long workedMinutes = Duration.between(
                    attendance.getCheckIn(),
                    attendance.getCheckOut()
            ).toMinutes();

            double workedHours = workedMinutes / 60.0;

            // Current standard working hours
            double normalWorkingHours = 8.0;

            double overtimeHours =
                    Math.max(0, workedHours - normalWorkingHours);

            attendance.setOvertimeHours(overtimeHours);
        } else {
            attendance.setOvertimeHours(0);
        }
    }

    // Delete attendance
    public void deleteAttendance(int id) {

        attendanceRepository.deleteById(id);
    }

    // Today's attendance
    public List<Attendance> getTodayAttendance() {

        return attendanceRepository.findByDate(LocalDate.now());
    }

    // Employee attendance
    public List<Attendance> getEmployeeAttendance(int employeeid) {

        return attendanceRepository
                .findByEmployeeEmployeeid(employeeid);
    }

    // Monthly attendance
    public List<Attendance> getMonthlyAttendance(
            int employeeid,
            int year,
            int month) {

        LocalDate startDate =
                LocalDate.of(year, month, 1);

        LocalDate endDate =
                startDate.withDayOfMonth(
                        startDate.lengthOfMonth());

        return attendanceRepository
                .findByEmployeeEmployeeidAndDateBetween(
                        employeeid,
                        startDate,
                        endDate
                );
    }
}