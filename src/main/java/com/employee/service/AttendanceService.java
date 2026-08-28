package com.employee.service;

import com.employee.entity.Attendance;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {


    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }


    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(int id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    public Attendance addAttendance(Attendance attendance) {

        if (attendance.getCheckIn() != null && attendance.getCheckOut() != null) {

            long workedMinutes = java.time.Duration.between(
                    attendance.getCheckIn(),
                    attendance.getCheckOut()
            ).toMinutes();

            double workedHours = workedMinutes / 60.0;

            double overtimeHours = Math.max(0, workedHours - 8);

            attendance.setOvertimeHours(overtimeHours);
        }

        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Attendance attendance) {

        if (attendance.getCheckIn() != null && attendance.getCheckOut() != null) {

            long workedMinutes = java.time.Duration.between(
                    attendance.getCheckIn(),
                    attendance.getCheckOut()
            ).toMinutes();

            double workedHours = workedMinutes / 60.0;

            double overtimeHours = Math.max(0, workedHours - 8);

            attendance.setOvertimeHours(overtimeHours);
        }

        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(int id) {
        attendanceRepository.deleteById(id);
    }
    public List<Attendance> getTodayAttendance() {
        return attendanceRepository.findByDate(LocalDate.now());
    }

    public List<Attendance> getEmployeeAttendance(int employeeid) {
        return attendanceRepository.findByEmployeeEmployeeid(employeeid);
    }

    public List<Attendance> getMonthlyAttendance(int employeeid, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return attendanceRepository.findByEmployeeEmployeeidAndDateBetween(
                employeeid, startDate, endDate
        );
    }
}