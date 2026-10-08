package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.Experiment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExperimentDAO {

    public void addExperiment(Experiment experiment) throws Exception {
        String sql = "INSERT INTO experiments (project_id, dataset_id, name, description, model_name, parameters, metrics, status, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, experiment.getProjectId());
        ps.setInt(2, experiment.getDatasetId());
        ps.setString(3, experiment.getName());
        ps.setString(4, experiment.getDescription());
        ps.setString(5, experiment.getModelName());
        ps.setString(6, experiment.getParameters());
        ps.setString(7, experiment.getMetrics());
        ps.setString(8, experiment.getStatus());
        ps.setInt(9, experiment.getCreatedBy());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<Experiment> getAllExperiments() throws Exception {
        List<Experiment> experiments = new ArrayList<>();

        String sql = "SELECT * FROM experiments ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Experiment experiment = new Experiment();

            experiment.setId(rs.getInt("id"));
            experiment.setProjectId(rs.getInt("project_id"));
            experiment.setDatasetId(rs.getInt("dataset_id"));
            experiment.setName(rs.getString("name"));
            experiment.setDescription(rs.getString("description"));
            experiment.setModelName(rs.getString("model_name"));
            experiment.setParameters(rs.getString("parameters"));
            experiment.setMetrics(rs.getString("metrics"));
            experiment.setStatus(rs.getString("status"));
            experiment.setCreatedBy(rs.getInt("created_by"));

            experiments.add(experiment);
        }

        rs.close();
        ps.close();
        con.close();

        return experiments;
    }

    public void updateExperiment(Experiment experiment) throws Exception {
        String sql = "UPDATE experiments SET project_id = ?, dataset_id = ?, name = ?, description = ?, model_name = ?, parameters = ?, metrics = ?, status = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, experiment.getProjectId());
        ps.setInt(2, experiment.getDatasetId());
        ps.setString(3, experiment.getName());
        ps.setString(4, experiment.getDescription());
        ps.setString(5, experiment.getModelName());
        ps.setString(6, experiment.getParameters());
        ps.setString(7, experiment.getMetrics());
        ps.setString(8, experiment.getStatus());
        ps.setInt(9, experiment.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteExperiment(int id) throws Exception {
        String sql = "DELETE FROM experiments WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
