package com.employee.repository;

import com.employee.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByEmployeeEmployeeid(int employeeid);

    List<Attendance> findByEmployeeEmployeeidAndDateBetween(
            int employeeid,
            LocalDate startDate,
            LocalDate endDate
    );

    @Modifying
    @Query("DELETE FROM Attendance a WHERE a.employee.employeeid = :employeeid")
    void deleteByEmployeeEmployeeid(@Param("employeeid") int employeeid);
}