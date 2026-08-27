package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:./data/mydb";
            String username = "sa";
            String password = "";

            return DriverManager.getConnection(url, username, password);
        }catch(ClassNotFoundException e){
            throw new SQLException("h2 driver not found");
        }
    }
}
