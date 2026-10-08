package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {

    public void addResource(Resource resource) throws Exception {

        String sql = "INSERT INTO resources (name, type, description, location, status) VALUES (?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, resource.getName());
        ps.setString(2, resource.getType());
        ps.setString(3, resource.getDescription());
        ps.setString(4, resource.getLocation());
        ps.setString(5, resource.getStatus());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<Resource> getAllResources() throws Exception {

        List<Resource> resources = new ArrayList<>();

        String sql = "SELECT * FROM resources ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Resource resource = new Resource();

            resource.setId(rs.getInt("id"));
            resource.setName(rs.getString("name"));
            resource.setType(rs.getString("type"));
            resource.setDescription(rs.getString("description"));
            resource.setLocation(rs.getString("location"));
            resource.setStatus(rs.getString("status"));

            resources.add(resource);
        }

        rs.close();
        ps.close();
        con.close();

        return resources;
    }

    public void updateResource(Resource resource) throws Exception {

        String sql = "UPDATE resources SET name = ?, type = ?, description = ?, location = ?, status = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, resource.getName());
        ps.setString(2, resource.getType());
        ps.setString(3, resource.getDescription());
        ps.setString(4, resource.getLocation());
        ps.setString(5, resource.getStatus());
        ps.setInt(6, resource.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteResource(int id) throws Exception {

        String sql = "DELETE FROM resources WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
