package com.employee.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "date"})
        }
)
public class Attendance {

    private int id;
    private Employee employee;
    private LocalDate date;
    private AttendanceStatus status;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private double overtimeHours;

    public Attendance() {
    }

    public Attendance(int id, Employee employee, LocalDate date,
                      AttendanceStatus status, LocalTime checkIn,
                      LocalTime checkOut) {
        this.id = id;
        this.employee = employee;
        this.date = date;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Enumerated(EnumType.STRING)
    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }
    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }


    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", employee=" + employee.getEmployeeid() +
                ", date=" + date +
                ", status=" + status +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}