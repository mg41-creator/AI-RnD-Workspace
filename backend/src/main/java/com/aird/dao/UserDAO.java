package com.aird.dao;
import com.aird.DatabaseConnection;
import com.aird.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void addUser(User user) throws Exception {

        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getRole());

        ps.executeUpdate();

        ps.close();
        con.close();
    }
    public User findUserByEmail(String email) throws Exception {

        String sql = "SELECT * FROM users WHERE email = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            rs.close();
            ps.close();
            con.close();
            return user;
        }
        rs.close();
        ps.close();
        con.close();
        return null;
    }
    public User loginUser(String email, String password) throws Exception {

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            rs.close();
            ps.close();
            con.close();

            return user;
        }
        rs.close();
        ps.close();
        con.close();

        return null;
    }
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));

            users.add(user);
        }

        rs.close();
        ps.close();
        con.close();

        return users;
    }

    public void updateUser(User user) throws Exception {
        String sql = "UPDATE users SET name = ?, email = ?, role = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getRole());
        ps.setInt(4, user.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteUser(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}