package com.employee.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    private int employeeid;
    private String employeName;
    private String department;
    private double salary;

    public Employee() {
    }

    public Employee(int employeeid, String employeName, String department, double salary) {
        this.employeeid = employeeid;
        this.employeName = employeName;
        this.department = department;
        this.salary = salary;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getEmployeName() {
        return employeName;
    }

    public void setEmployeName(String employeName) {
        this.employeName = employeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + employeeid +
                ", Name: " + employeName +
                ", Department: " + department +
                ", Salary: " + salary;
    }
}