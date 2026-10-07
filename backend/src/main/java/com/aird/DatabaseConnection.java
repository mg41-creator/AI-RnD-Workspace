package com.aird;
import java.sql.*;
public class DatabaseConnection {
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/ai_rnd_workspace";
        String username = "root";
        String password = "Madhav@12345@airnd";
        return DriverManager.getConnection(url,username,password);
    }
}
