package com.mycompany.corporatetalenthub.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/corporate_talent_hub";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Zxc.123*";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
