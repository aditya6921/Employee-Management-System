package service;

import model.Employee;
import exception.EmployeeNotFoundException;
import exception.DuplicateEmployeeException;

import java.io.IOException;
import java.util.*;
import filereadandwrite.Fileutil;

public class EmployeManager {

    private List<Employee> employe = new ArrayList<>();
    private Fileutil fileUtil = new Fileutil();

    //add
    public void addEmploye(int employeeid, String employeName,
                           String department, double salary) {

        // validation
        if (employeeid < 0) {
            throw new IllegalArgumentException("Employee ID cannot be negative");
        }

        if (employeName == null || employeName.trim().isEmpty() || !employeName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Employee name must contain only letters no space and numbers");
        }

        if (department == null || department.trim().isEmpty() || !department.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Employee name must contain only letters no space and numbers");
        }

        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        // Check duplicate ID
        if (searchEmploye(employeeid) != null) {
            throw new DuplicateEmployeeException(
                    "Employee with ID " + employeeid + " already exists"
            );
        }

        employe.add(new Employee(employeeid, employeName, department, salary));
    }


    //search
    public Employee searchEmploye(int employeeid) {

        for (Employee e : employe) {

            if (e != null && e.getEmployeeid() == employeeid) {
                return e;
            }
        }

        return null;
    }


    //view
    public void viewEmployee() {

        for (Employee e : employe) {
            System.out.println(e);
        }
    }


    //delete
    public void deleteEmployee(int delete) {

        Employee employee = searchEmploye(delete);

        if (employee == null) {
            throw new EmployeeNotFoundException(
                    "Employee with ID " + delete + " not found"
            );
        }

        employe.remove(employee);

        System.out.println("Employee deleted successfully");
    }

    //sorting
    public void sortbyName(){
        employe.sort(Comparator.comparing(e -> e.getEmployeName()));
        employe.forEach(e -> System.out.println(e));
    }
    public void sortbyId(){
        employe.sort(Comparator.comparing(e -> e.getEmployeeid()));
        employe.forEach(e -> System.out.println(e));
    }
    public void sortbyDepartment(){
        employe.sort(Comparator.comparing(e -> e.getDepartment()));
        employe.forEach(e -> System.out.println(e));
    }
    public void sortbySalary(){
        employe.sort(Comparator.comparing(e -> e.getSalary()));
        employe.forEach(e -> System.out.println(e));
    }


    //savetofile
    public void saveToFile() throws IOException {
        fileUtil.saveToFile(employe);
    }

    //loading file
    public void loadFromFile() throws IOException {
        employe = fileUtil.loadFromFile();
    }
}
