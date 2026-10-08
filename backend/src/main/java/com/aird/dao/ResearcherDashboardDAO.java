package com.aird.dao;

import com.aird.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResearcherDashboardDAO {

    public int getDatasetCount(int userId) throws Exception {
        return getCount(
                "SELECT COUNT(*) FROM datasets WHERE uploaded_by = ?",
                userId
        );
    }

    public int getExperimentCount(int userId) throws Exception {
        return getCount(
                "SELECT COUNT(*) FROM experiments WHERE created_by = ?",
                userId
        );
    }

    public int getTrainingCount(int userId) throws Exception {
        return getCount(
                "SELECT COUNT(*) FROM model_trainings WHERE started_by = ?",
                userId
        );
    }

    public int getCollaborationCount(int userId) throws Exception {
        return getCount(
                "SELECT COUNT(*) FROM collaborations WHERE user_id = ?",
                userId
        );
    }

    private int getCount(String sql, int userId) throws Exception {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        int count = 0;

        if (rs.next()) {
            count = rs.getInt(1);
        }

        rs.close();
        ps.close();
        con.close();

        return count;
    }
}
