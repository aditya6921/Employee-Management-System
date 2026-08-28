package com.employee.controller;

import com.employee.entity.Attendance;
import com.employee.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable int id) {
        return attendanceService.getAttendanceById(id);
    }

    @PostMapping
    public Attendance addAttendance(@RequestBody Attendance attendance) {
        return attendanceService.addAttendance(attendance);
    }

    @PutMapping
    public Attendance updateAttendance(@RequestBody Attendance attendance) {
        return attendanceService.updateAttendance(attendance);
    }

    @DeleteMapping("/{id}")
    public String deleteAttendance(@PathVariable int id) {
        attendanceService.deleteAttendance(id);
        return "Attendance deleted successfully";
    }

    @GetMapping("/today")
    public List<Attendance> getTodayAttendance() {
        return attendanceService.getTodayAttendance();
    }

    @GetMapping("/employee/{employeeid}")
    public List<Attendance> getEmployeeAttendance(@PathVariable int employeeid) {
        return attendanceService.getEmployeeAttendance(employeeid);
    }

    @GetMapping("/employee/{employeeid}/month/{year}/{month}")
    public List<Attendance> getMonthlyAttendance(
            @PathVariable int employeeid,
            @PathVariable int year,
            @PathVariable int month) {

        return attendanceService.getMonthlyAttendance(employeeid, year, month);
    }
}