package com.aird.dao;

import com.aird.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboardDAO {

    public int getUserCount() throws Exception {
        return getCount("SELECT COUNT(*) FROM users");
    }

    public int getProjectCount() throws Exception {
        return getCount("SELECT COUNT(*) FROM projects");
    }

    public int getResourceCount() throws Exception {
        return getCount("SELECT COUNT(*) FROM resources");
    }

    public int getUsageLogCount() throws Exception {
        return getCount("SELECT COUNT(*) FROM usage_logs");
    }

    private int getCount(String sql) throws Exception {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
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
