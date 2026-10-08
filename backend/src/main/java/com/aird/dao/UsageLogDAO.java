package com.aird.dao;

import com.aird.DatabaseConnection;
import com.aird.model.UsageLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsageLogDAO {

    public void addLog(UsageLog log) throws Exception {
        String sql = "INSERT INTO usage_logs (user_id, project_id, resource_id, action, details) VALUES (?, ?, ?, ?, ?)";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, log.getUserId());

        if (log.getProjectId() > 0) {
            ps.setInt(2, log.getProjectId());
        } else {
            ps.setNull(2, java.sql.Types.INTEGER);
        }

        if (log.getResourceId() > 0) {
            ps.setInt(3, log.getResourceId());
        } else {
            ps.setNull(3, java.sql.Types.INTEGER);
        }

        ps.setString(4, log.getAction());
        ps.setString(5, log.getDetails());

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public List<UsageLog> getAllLogs() throws Exception {
        List<UsageLog> logs = new ArrayList<>();

        String sql = "SELECT * FROM usage_logs ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            UsageLog log = new UsageLog();

            log.setId(rs.getInt("id"));
            log.setUserId(rs.getInt("user_id"));
            log.setProjectId(rs.getInt("project_id"));
            log.setResourceId(rs.getInt("resource_id"));
            log.setAction(rs.getString("action"));
            log.setDetails(rs.getString("details"));

            logs.add(log);
        }

        rs.close();
        ps.close();
        con.close();

        return logs;
    }

    public List<UsageLog> getLogsByUser(int userId) throws Exception {
        List<UsageLog> logs = new ArrayList<>();

        String sql = "SELECT * FROM usage_logs WHERE user_id = ? ORDER BY id DESC";

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            UsageLog log = new UsageLog();

            log.setId(rs.getInt("id"));
            log.setUserId(rs.getInt("user_id"));
            log.setProjectId(rs.getInt("project_id"));
            log.setResourceId(rs.getInt("resource_id"));
            log.setAction(rs.getString("action"));
            log.setDetails(rs.getString("details"));

            logs.add(log);
        }

        rs.close();
        ps.close();
        con.close();

        return logs;
    }
}
