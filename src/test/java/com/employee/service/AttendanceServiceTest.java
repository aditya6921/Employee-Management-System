package com.employee.service;

import com.employee.entity.Attendance;
import com.employee.repository.AttendanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    @Test
    void getAllAttendance() {
        List<Attendance> list = List.of(new Attendance(), new Attendance());
        when(attendanceRepository.findAll()).thenReturn(list);

        assertEquals(2, attendanceService.getAllAttendance().size());
        verify(attendanceRepository).findAll();
    }

    @Test
    void getAttendanceById() {
        Attendance attendance = new Attendance();
        when(attendanceRepository.findById(1))
                .thenReturn(Optional.of(attendance));

        assertSame(attendance, attendanceService.getAttendanceById(1));
    }

    @Test
    void getAttendanceByIdNotFound() {
        when(attendanceRepository.findById(99))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> attendanceService.getAttendanceById(99)
        );

        assertEquals("Attendance not found", exception.getMessage());
    }

    @Test
    void addAttendanceCalculatesOvertimeAndSaves() {
        Attendance attendance = new Attendance();
        attendance.setCheckIn(LocalTime.of(9, 0));
        attendance.setCheckOut(LocalTime.of(18, 0));

        when(attendanceRepository.save(attendance)).thenReturn(attendance);

        Attendance result = attendanceService.addAttendance(attendance);

        assertSame(attendance, result);
        assertEquals(1.0, attendance.getOvertimeHours(), 0.001);
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void updateAttendanceCalculatesOvertimeAndSaves() {
        Attendance attendance = new Attendance();
        attendance.setCheckIn(LocalTime.of(9, 0));
        attendance.setCheckOut(LocalTime.of(17, 0));

        when(attendanceRepository.save(attendance)).thenReturn(attendance);

        attendanceService.updateAttendance(attendance);

        assertEquals(0.0, attendance.getOvertimeHours(), 0.001);
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void addAttendanceWithoutCheckInOrOutSetsOvertimeToZero() {
        Attendance attendance = new Attendance();

        attendanceService.addAttendance(attendance);

        assertEquals(0.0, attendance.getOvertimeHours(), 0.001);
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void deleteAttendance() {
        attendanceService.deleteAttendance(1);

        verify(attendanceRepository).deleteById(1);
    }

    @Test
    void getTodayAttendance() {
        List<Attendance> list = List.of(new Attendance());
        when(attendanceRepository.findByDate(LocalDate.now()))
                .thenReturn(list);

        assertEquals(1, attendanceService.getTodayAttendance().size());
        verify(attendanceRepository).findByDate(LocalDate.now());
    }

    @Test
    void getEmployeeAttendance() {
        List<Attendance> list = List.of(new Attendance());
        when(attendanceRepository.findByEmployeeEmployeeid(5))
                .thenReturn(list);

        assertEquals(1, attendanceService.getEmployeeAttendance(5).size());
        verify(attendanceRepository).findByEmployeeEmployeeid(5);
    }

    @Test
    void getMonthlyAttendance() {
        List<Attendance> list = List.of(new Attendance());

        when(attendanceRepository
                .findByEmployeeEmployeeidAndDateBetween(
                        5,
                        LocalDate.of(2026, 2, 1),
                        LocalDate.of(2026, 2, 28)
                )).thenReturn(list);

        assertEquals(
                1,
                attendanceService.getMonthlyAttendance(5, 2026, 2).size()
        );
    }
}