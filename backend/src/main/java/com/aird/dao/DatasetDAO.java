package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.Dataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatasetDAO {

    public void addDataset(Dataset dataset) throws Exception {
        String sql = "INSERT INTO datasets (name, description, file_path, format, size_mb, uploaded_by, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, dataset.getName());
        ps.setString(2, dataset.getDescription());
        ps.setString(3, dataset.getFilePath());
        ps.setString(4, dataset.getFormat());
        ps.setDouble(5, dataset.getSizeMb());
        ps.setInt(6, dataset.getUploadedBy());
        ps.setString(7, dataset.getStatus());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<Dataset> getAllDatasets() throws Exception {
        List<Dataset> datasets = new ArrayList<>();

        String sql = "SELECT * FROM datasets ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Dataset dataset = new Dataset();

            dataset.setId(rs.getInt("id"));
            dataset.setName(rs.getString("name"));
            dataset.setDescription(rs.getString("description"));
            dataset.setFilePath(rs.getString("file_path"));
            dataset.setFormat(rs.getString("format"));
            dataset.setSizeMb(rs.getDouble("size_mb"));
            dataset.setUploadedBy(rs.getInt("uploaded_by"));
            dataset.setStatus(rs.getString("status"));

            datasets.add(dataset);
        }

        rs.close();
        ps.close();
        con.close();

        return datasets;
    }

    public void updateDataset(Dataset dataset) throws Exception {
        String sql = "UPDATE datasets SET name = ?, description = ?, file_path = ?, format = ?, size_mb = ?, status = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, dataset.getName());
        ps.setString(2, dataset.getDescription());
        ps.setString(3, dataset.getFilePath());
        ps.setString(4, dataset.getFormat());
        ps.setDouble(5, dataset.getSizeMb());
        ps.setString(6, dataset.getStatus());
        ps.setInt(7, dataset.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteDataset(int id) throws Exception {
        String sql = "DELETE FROM datasets WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}