package com.aird.servlet;

import com.aird.dao.AdminDashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final AdminDashboardDAO dashboardDAO = new AdminDashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            int users = dashboardDAO.getUserCount();
            int projects = dashboardDAO.getProjectCount();
            int resources = dashboardDAO.getResourceCount();
            int usageLogs = dashboardDAO.getUsageLogCount();

            response.getWriter().println("Admin Dashboard");
            response.getWriter().println("-------------------------");
            response.getWriter().println("Total Users: " + users);
            response.getWriter().println("Total Projects: " + projects);
            response.getWriter().println("Total Resources: " + resources);
            response.getWriter().println("Total Usage Logs: " + usageLogs);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading admin dashboard!");
        }
    }
}
