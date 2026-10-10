package com.aird;

import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() throws Exception {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || username == null || password == null) {
            throw new SQLException("Database environment variables are not configured.");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}

