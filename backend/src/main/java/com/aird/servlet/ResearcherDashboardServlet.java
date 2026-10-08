package com.aird.servlet;

import com.aird.dao.ResearcherDashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/researcher/dashboard")
public class ResearcherDashboardServlet extends HttpServlet {

    private final ResearcherDashboardDAO dashboardDAO = new ResearcherDashboardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            int datasets = dashboardDAO.getDatasetCount(userId);
            int experiments = dashboardDAO.getExperimentCount(userId);
            int trainings = dashboardDAO.getTrainingCount(userId);
            int collaborations = dashboardDAO.getCollaborationCount(userId);

            response.getWriter().println("Researcher Dashboard");
            response.getWriter().println("-------------------------");
            response.getWriter().println("Total Datasets: " + datasets);
            response.getWriter().println("Total Experiments: " + experiments);
            response.getWriter().println("Total Model Trainings: " + trainings);
            response.getWriter().println("Total Collaborations: " + collaborations);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading researcher dashboard!");
        }
    }
}
