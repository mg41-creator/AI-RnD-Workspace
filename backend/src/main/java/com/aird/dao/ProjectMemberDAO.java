package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.ProjectMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberDAO {

    // Add a member to a project
    public void addMember(ProjectMember member) throws Exception {

        String sql = "INSERT INTO project_members (project_id, user_id, role) VALUES (?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, member.getProjectId());
        ps.setInt(2, member.getUserId());
        ps.setString(3, member.getRole());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    // Get all members of a project
    public List<ProjectMember> getMembersByProject(int projectId) throws Exception {

        List<ProjectMember> members = new ArrayList<>();

        String sql = "SELECT * FROM project_members WHERE project_id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, projectId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            ProjectMember member = new ProjectMember();

            member.setId(rs.getInt("id"));
            member.setProjectId(rs.getInt("project_id"));
            member.setUserId(rs.getInt("user_id"));
            member.setRole(rs.getString("role"));

            members.add(member);
        }

        rs.close();
        ps.close();
        con.close();

        return members;
    }

    // Remove a member from a project
    public void removeMember(int projectId, int userId) throws Exception {

        String sql = "DELETE FROM project_members WHERE project_id = ? AND user_id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, projectId);
        ps.setInt(2, userId);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
