import java.util.Scanner;

public class Main{
    static int[] employeeid = new int[10];
    static String[] employeName = new String[10];
    static String[] Department = new String[10];
    static double[] salary = new double[10];

    static Scanner s = new Scanner(System.in);
    static int count =0;

    public static void addEmployee(){

        System.out.println("how many employess you want to add");
        int n = s.nextInt();
        s.nextLine();

        if(count+n > 10){
            System.out.println("only"+(10-count)+"places avialable");
        }
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Employee ID: ");
            employeeid[count] = s.nextInt();
            s.nextLine();
            System.out.print("Enter Employee Name: ");
            employeName[count] = s.nextLine();

            System.out.print("Enter Department: ");
            Department[count] = s.nextLine();

            System.out.print("Enter Salary: ");
            salary[count] = s.nextDouble();
            s.nextLine();
            count ++;
        }

    }
    public static void searchEmployee(){
        System.out.println("Enter the Employee Id to Search Employee : ");
        int search = s.nextInt();
        for(int i = 0 ; i<count ; i++){
            if(search == employeeid[i]){
                System.out.println("Employee Found ");
                System.out.println("Employee ID : " + employeeid[i]);
                System.out.println("Employee Name : " + employeName[i]);
                System.out.println("Employee Department : " + Department[i]);
                System.out.println("Employee salary : " + salary[i]);

            }
        }


    }

    public static void deleteEmployee(){
        System.out.println("Enter the Employee Id which to be deleted : ");
        int delete = s.nextInt();
        s.nextLine();
        for(int i = 0 ; i< count ; i++) {
            if (delete == employeeid[i]) {
                for(int j = i;j<count-1;j++) {

                    employeeid[j] = employeeid[j + 1];
                    employeName[j] = employeName[j + 1];
                    Department[j] = Department[j + 1];
                    salary[j] = salary[j + 1];
                    System.out.println("Employee deleted");
                    count--;
                }
            }
        }

    }



    public static void viewEmployee(){
        for(int i = 0; i < count ; i++) {
            System.out.println("Employee ID : " + employeeid[i]);
            System.out.println("Employee Name : " + employeName[i]);
            System.out.println("Employee Department : " + Department[i]);
            System.out.println("Employee salary : " + salary[i]);
        }
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