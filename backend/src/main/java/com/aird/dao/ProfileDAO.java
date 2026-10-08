package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProfileDAO {

    public void updateProfile(User user) throws Exception {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setInt(3, user.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void updatePassword(int userId, String password) throws Exception {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, password);
        ps.setInt(2, userId);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
