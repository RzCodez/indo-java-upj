package login;

import java.sql.*;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_barang";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;


    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("Database connected!");
            } catch (SQLException e) {
                System.out.println("Failed to connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    
}
