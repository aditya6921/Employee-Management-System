package testing;

import database.DatabaseConnection;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("databse connected");
        } catch (Exception e) {
            System.out.println("connection failed");
            e.printStackTrace();

        }
    }
}
