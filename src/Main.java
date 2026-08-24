import model.Employee;
import service.EmployeManager;
import ui.EmployeeMenu;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){

        Scanner s = new Scanner(System.in);
        System.out.println("Hello world");
        EmployeManager manager = new EmployeManager();
        EmployeeMenu menu = new EmployeeMenu(manager);
        menu.start();
    }
}