package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // Create a new project
    public void addProject(Project project) throws Exception {

        String sql = "INSERT INTO projects (title, description, created_by, status) VALUES (?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, project.getTitle());
        ps.setString(2, project.getDescription());
        ps.setInt(3, project.getCreatedBy());
        ps.setString(4, project.getStatus());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    // Get all projects
    public List<Project> getAllProjects() throws Exception {

        List<Project> projects = new ArrayList<>();

        String sql = "SELECT * FROM projects ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Project project = new Project();

            project.setId(rs.getInt("id"));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setCreatedBy(rs.getInt("created_by"));
            project.setStatus(rs.getString("status"));

            projects.add(project);
        }

        rs.close();
        ps.close();
        con.close();

        return projects;
    }
    // Update a project
    public void updateProject(Project project) throws Exception {

        String sql = "UPDATE projects SET title = ?, description = ?, status = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, project.getTitle());
        ps.setString(2, project.getDescription());
        ps.setString(3, project.getStatus());
        ps.setInt(4, project.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }


    // Delete a project
    public void deleteProject(int id) throws Exception {

        String sql = "DELETE FROM projects WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
