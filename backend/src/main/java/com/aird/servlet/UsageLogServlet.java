package com.aird.servlet;

import com.aird.dao.UsageLogDAO;
import com.aird.model.UsageLog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/usage-logs")
public class UsageLogServlet extends HttpServlet {

    private final UsageLogDAO usageLogDAO = new UsageLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            UsageLog log = new UsageLog();

            log.setUserId(Integer.parseInt(request.getParameter("userId")));
            log.setProjectId(Integer.parseInt(request.getParameter("projectId")));
            log.setResourceId(Integer.parseInt(request.getParameter("resourceId")));
            log.setAction(request.getParameter("action"));
            log.setDetails(request.getParameter("details"));

            usageLogDAO.addLog(log);

            response.getWriter().println("Usage log added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Usage log operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            String userId = request.getParameter("userId");

            List<UsageLog> logs;

            if (userId != null && !userId.isEmpty()) {
                logs = usageLogDAO.getLogsByUser(Integer.parseInt(userId));
            } else {
                logs = usageLogDAO.getAllLogs();
            }

            if (logs.isEmpty()) {
                response.getWriter().println("No usage logs found.");
                return;
            }

            for (UsageLog log : logs) {
                response.getWriter().println("Log ID: " + log.getId());
                response.getWriter().println("User ID: " + log.getUserId());
                response.getWriter().println("Project ID: " + log.getProjectId());
                response.getWriter().println("Resource ID: " + log.getResourceId());
                response.getWriter().println("Action: " + log.getAction());
                response.getWriter().println("Details: " + log.getDetails());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading usage logs!");
        }
    }
}