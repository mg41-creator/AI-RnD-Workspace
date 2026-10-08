package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.ModelTraining;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModelTrainingDAO {

    public void addTraining(ModelTraining training) throws Exception {
        String sql = "INSERT INTO model_trainings (project_id, dataset_id, experiment_id, model_name, algorithm, parameters, accuracy, loss, status, started_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, training.getProjectId());
        ps.setInt(2, training.getDatasetId());
        ps.setInt(3, training.getExperimentId());
        ps.setString(4, training.getModelName());
        ps.setString(5, training.getAlgorithm());
        ps.setString(6, training.getParameters());
        ps.setDouble(7, training.getAccuracy());
        ps.setDouble(8, training.getLoss());
        ps.setString(9, training.getStatus());
        ps.setInt(10, training.getStartedBy());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<ModelTraining> getAllTrainings() throws Exception {
        List<ModelTraining> trainings = new ArrayList<>();

        String sql = "SELECT * FROM model_trainings ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ModelTraining training = new ModelTraining();

            training.setId(rs.getInt("id"));
            training.setProjectId(rs.getInt("project_id"));
            training.setDatasetId(rs.getInt("dataset_id"));
            training.setExperimentId(rs.getInt("experiment_id"));
            training.setModelName(rs.getString("model_name"));
            training.setAlgorithm(rs.getString("algorithm"));
            training.setParameters(rs.getString("parameters"));
            training.setAccuracy(rs.getDouble("accuracy"));
            training.setLoss(rs.getDouble("loss"));
            training.setStatus(rs.getString("status"));
            training.setStartedBy(rs.getInt("started_by"));

            trainings.add(training);
        }

        rs.close();
        ps.close();
        con.close();

        return trainings;
    }

    public void updateTraining(ModelTraining training) throws Exception {
        String sql = "UPDATE model_trainings SET project_id = ?, dataset_id = ?, experiment_id = ?, model_name = ?, algorithm = ?, parameters = ?, accuracy = ?, loss = ?, status = ? WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, training.getProjectId());
        ps.setInt(2, training.getDatasetId());
        ps.setInt(3, training.getExperimentId());
        ps.setString(4, training.getModelName());
        ps.setString(5, training.getAlgorithm());
        ps.setString(6, training.getParameters());
        ps.setDouble(7, training.getAccuracy());
        ps.setDouble(8, training.getLoss());
        ps.setString(9, training.getStatus());
        ps.setInt(10, training.getId());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public void deleteTraining(int id) throws Exception {
        String sql = "DELETE FROM model_trainings WHERE id = ?";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
