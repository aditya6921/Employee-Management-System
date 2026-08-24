package service;

import model.Employee;

import java.util.*;

public class EmployeManager {

    List<Employee> employe = new ArrayList<>();
    Scanner s = new Scanner(System.in);

    //add
    public void addEmploye(int employeeid,String employeName, String department,double salary){

        employe.add(new Employee(employeeid,employeName,department,salary));
    }

    //search
    public Employee searchEmploye(int employeeid){
        for(int i=0; i<employe.size(); i++){

            if(employe.get(i) != null && employe.get(i).getEmployeeid() == employeeid){
                return employe.get(i);
            }
        }
        return null;
    }

    //view
    public void viewEmployee(){
        for(int i = 0;i<employe.size() ;i++){
            Employee e = employe.get(i);
            System.out.println(e);
        }
    }

    //delete
    public void deleteEmployee(int delete){
        for(int  i = 0 ; i<employe.size(); i++){
            if(employe.get(i) != null && employe.get(i).getEmployeeid() == delete){
                employe.remove(i);
                System.out.println("Employee deleted successfully");
                return;
            }
        }

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
}
