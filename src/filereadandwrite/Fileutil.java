package filereadandwrite;

import model.Employee;

import java.io.*;
import java.util.*;

public class Fileutil {

    private static final String FILE_NAME = "employees.txt";

    public void saveToFile(List<Employee> employees) throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Employee e : employees) {

                writer.println(
                        e.getEmployeeid() + "," +
                                e.getEmployeName() + "," +
                                e.getDepartment() + "," +
                                e.getSalary()
                );
            }

        }

    }

    public List<Employee> loadFromFile() throws IOException {

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String department = data[2];
                double salary = Double.parseDouble(data[3]);

                Employee employee = new Employee(
                        id,
                        name,
                        department,
                        salary
                );

                employees.add(employee);
            }
        }

        return employees;
    }


}
