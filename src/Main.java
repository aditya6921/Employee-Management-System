import java.util.Scanner;

public class Main{
    static int employeeid;
    static String employeName;
    static String Department;
    static double salary;

    static Scanner s = new Scanner(System.in);

    public static void addEmployee(){
        System.out.print("Enter Employee ID: ");
        employeeid =s.nextInt();
        s.nextLine();
        System.out.print("Enter Employee Name: ");
        employeName =s.nextLine();

        System.out.print("Enter Department: ");
        Department =s.nextLine();

        System.out.print("Enter Salary: ");
        salary = s.nextDouble();
    }

    public static void searchEmployee(){
        System.out.println("Enter the Employee Id to Serach Employee : ");
        int search = s.nextInt();
        if(search == employeeid){
            System.out.println("Employee Found ");
            viewEmployee();
        }else{
            System.out.println("Employee Not Found");
        }
    }

    public static void deleteEmployee(){
        System.out.println("Enter the Employee Id which to be deleted : ");
        int delete = s.nextInt();
        if(delete == employeeid){
            employeeid = 0;
            employeName = "";
            Department = "";
            salary = 0;
            System.out.println("Employee deleted");
        }else{
            System.out.println("Employee Not Found");
        }
    }

    public static void viewEmployee(){
        System.out.println("Employee ID : "+employeeid);
        System.out.println("Employee Name : "+employeName);
        System.out.println("Employee Department : "+Department);
        System.out.println("Employee salary : "+salary);
    }
    public static void main(String[] args){

        System.out.println("Hello world");
        int num;
        do {
            System.out.println("=======Employee management and attendence system=======");
            System.out.println(" 1. Add Employee\n 2. Search Employee\n 3. Delete Employee\n 4. View Employee\n 5. Exit\n==========================");

            num = s.nextInt();
            switch (num) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    searchEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    viewEmployee();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Please enter valid choice (Between 1-5");
            }
        }while(num != 5);
    }
}