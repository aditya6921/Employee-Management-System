package service;

import model.Employee;

import java.util.Scanner;

public class EmployeManager {

    Employee[] employes = new Employee[100];
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //add
    public void addEmploye(int employeeid,String employeName, String department,double salary){
        if (count >= employes.length) {
            System.out.println("Employee storage is full.");
            return;
        }
        employes[count] = new Employee(employeeid,employeName,department,salary);
        count ++;
    }

    //search
    public Employee searchEmploye(int employeeid){
        for(int i=0;i<count;i++){

            if(employes[i] != null && employes[i].getEmployeeid() == employeeid){
                return employes[i];
            }
        }
        return null;
    }

    //view
    public Employee viewEmployee(){
        for(int i = 0;i<count ;i++){
            Employee e = employes[i];
            System.out.println(e);
        }
        return null;
    }

    //delete
    public void deleteEmployee(int delete){
        for(int  i = 0 ; i<count; i++){
            if(employes[i] != null && employes[i].getEmployeeid() == delete){
                for(int j = i;j<count;j++){
                    employes[j] = employes[j+1];
                }
                count --;
                System.out.println("Employee deleted successfully");
                return;
            }
        }

    }
}
