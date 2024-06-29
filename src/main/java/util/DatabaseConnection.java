package main.java.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost/library";
    private static final String root = "root";
    
    static {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }
  
   
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, root, "");
    }
}
