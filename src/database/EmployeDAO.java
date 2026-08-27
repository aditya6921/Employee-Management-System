package database;

import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {
    // create
    public void addEmployee(Employee e) {
        String sql = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, e.getEmployeeid());
            statement.setString(2, e.getEmployeName());
            statement.setString(3, e.getDepartment());
            statement.setDouble(4, e.getSalary());

            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // READ ONE
    public Employee searchEmployee(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Employee(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("department"),
                            result.getDouble("salary")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // read
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Employee e = new Employee(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("department"),
                        result.getDouble("salary")
                );

                employees.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employees;
    }

    //update
    public void updateEmployee(Employee e) {
        String sql = "UPDATE employees SET name = ?, department = ?, salary = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getEmployeName());
            statement.setString(2, e.getDepartment());
            statement.setDouble(3, e.getSalary());
            statement.setInt(4, e.getEmployeeid());

            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //delete
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void sortByName(){
        String sql = "Select * from employees ORDER BY name";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("id") + " " +
                                resultSet.getString("name") + " " +
                                resultSet.getString("department") + " " +
                                resultSet.getDouble("salary")
                );
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public void sortByDepartment(){
        String sql = "SELECT * FROM employees ORDER BY department";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("id") + " " +
                                resultSet.getString("name") + " " +
                                resultSet.getString("department") + " " +
                                resultSet.getDouble("salary")
                );
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void sortById(){
        String sql = "SELECT * FROM employees ORDER BY id";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("id") + " " +
                                resultSet.getString("name") + " " +
                                resultSet.getString("department") + " " +
                                resultSet.getDouble("salary")
                );
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void sortBySalary(){
        String sql = "SELECT * FROM employees ORDER BY salary";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("id") + " " +
                                resultSet.getString("name") + " " +
                                resultSet.getString("department") + " " +
                                resultSet.getDouble("salary")
                );
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
