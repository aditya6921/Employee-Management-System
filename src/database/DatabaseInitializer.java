package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initialize() {

        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "department VARCHAR(100), " +
                "salary DOUBLE)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();

            System.out.println("Employee table initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
