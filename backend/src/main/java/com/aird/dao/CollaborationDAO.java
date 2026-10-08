package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.Collaboration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CollaborationDAO {

    public void addCollaboration(Collaboration collaboration) throws Exception {
        String sql = "INSERT INTO collaborations (project_id, user_id, message, type) VALUES (?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, collaboration.getProjectId());
        ps.setInt(2, collaboration.getUserId());
        ps.setString(3, collaboration.getMessage());
        ps.setString(4, collaboration.getType());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<Collaboration> getByProject(int projectId) throws Exception {
        List<Collaboration> collaborations = new ArrayList<>();

        String sql = "SELECT * FROM collaborations WHERE project_id = ? ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, projectId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Collaboration collaboration = new Collaboration();

            collaboration.setId(rs.getInt("id"));
            collaboration.setProjectId(rs.getInt("project_id"));
            collaboration.setUserId(rs.getInt("user_id"));
            collaboration.setMessage(rs.getString("message"));
            collaboration.setType(rs.getString("type"));

            collaborations.add(collaboration);
        }

        rs.close();
        ps.close();
        con.close();

        return collaborations;
    }

    public void updateCollaboration(Collaboration collaboration) throws Exception {
        String sql = "UPDATE collaborations SET message = ?, type = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, collaboration.getMessage());
        ps.setString(2, collaboration.getType());
        ps.setInt(3, collaboration.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteCollaboration(int id) throws Exception {
        String sql = "DELETE FROM collaborations WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}