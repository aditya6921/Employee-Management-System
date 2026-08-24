package ui;

import model.Employee;
import service.EmployeManager;

import java.util.Scanner;

public class EmployeeMenu {
    private Scanner s;
    private EmployeManager manager;
    public EmployeeMenu(EmployeManager manager){
        this.manager = manager;
        this.s = new Scanner(System.in);

    }

    public void start() {
        int num;

        do {
            System.out.println("=======Employee management and attendence system=======");
            System.out.println(" 1. Add Employee\n 2. Search Employee\n 3. Delete Employee\n 4. View Employee\n 5. Sorting\n 6. Exit\n==========================");

            num = s.nextInt();
            switch (num) {
                case 1:

                    System.out.println("how many employees you want to add: ");
                    int n = s.nextInt();
                    s.nextLine();

                    for (int i = 0; i < n; i++) {

                        //taking all the fields from the user.
                        System.out.print("Enter EmployeeId :");
                        int id = s.nextInt();
                        s.nextLine();
                        System.out.print("Enter Employe name :");
                        String name = s.nextLine();
                        System.out.print("Enter Employe department :");
                        String department = s.nextLine();
                        System.out.print("Enter Employe salary :");
                        double salary = s.nextDouble();
                        s.nextLine();

                        //calling add employee().
                        manager.addEmploye(id, name, department, salary);
                    }
                    break;
                case 2:

                    System.out.println("Enter the id of the employee to search");
                    int id = s.nextInt();
                    s.nextLine();

                    //using oops funtionality , use object instead of printing all the values one by one.
                    Employee found = manager.searchEmploye(id);
                    if (found != null) {
                        System.out.println("Employee found successfully");
                        System.out.println(found);
                    } else {
                        System.out.println("employee not found");
                    }

                    break;

                case 3:
                    System.out.println("Enter the id of the employee you want to delete");
                    int delete = s.nextInt();
                    manager.deleteEmployee(delete);
                    break;
                case 4:
                    manager.viewEmployee();
                    break;
                case 5:
                    int a ;
                    do {
                        System.out.println("1. Sort by Name \n2. Sort by Salary \n3. Sort by Department \n4. Sort by Id \n5.Exit");
                        System.out.println("Select by which you have to sort : ");
                        a = s.nextInt();
                        switch (a) {
                            case 1:
                                manager.sortbyName();
                                break;
                            case 2:
                                manager.sortbySalary();
                                break;
                            case 3:
                                manager.sortbyDepartment();
                                break;
                            case 4:
                                manager.sortbyId();
                                break;
                            case 5 :
                                break;
                            default:
                                System.out.println("plz enter valid choice :");
                        }
                    }while(a !=5);

                    break;
                case 6:
                    break;
                default:
                    System.out.println("Please enter valid choice (Between 1-5");
            }
        } while (num != 6);
    }

}
