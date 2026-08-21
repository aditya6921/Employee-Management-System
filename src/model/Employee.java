package model;

public class Employee {

    //fields
    private int employeeid;
    private String employeName;
    private String department;
    private double salary;

    //counstructor
    public Employee(int employeeid,String employeName,String department,double salary){
        this.employeeid = employeeid;
        this.employeName = employeName;
        this.department = department;
        this.salary = salary;
    }

    //getters
    public int getEmployeeid(){
        return employeeid;
    }
    public String getEmployeName() {
        return employeName;
    }
    public String getDepartment() {
        return department;
    }
    public double getSalary() {
        return salary;
    }

    //setters
    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }
    public void setEmployeName(String employeName) {
        this.employeName = employeName;
    }
    public void setDepartment(String department) {
        this.department = department;
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
