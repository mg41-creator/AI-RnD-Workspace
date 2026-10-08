package com.aird.servlet;

import com.aird.dao.ExperimentDAO;
import com.aird.model.Experiment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/experiments")
public class ExperimentServlet extends HttpServlet {

    private final ExperimentDAO experimentDAO = new ExperimentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                Experiment experiment = new Experiment();

                experiment.setId(Integer.parseInt(request.getParameter("id")));
                experiment.setProjectId(Integer.parseInt(request.getParameter("projectId")));
                experiment.setDatasetId(Integer.parseInt(request.getParameter("datasetId")));
                experiment.setName(request.getParameter("name"));
                experiment.setDescription(request.getParameter("description"));
                experiment.setModelName(request.getParameter("modelName"));
                experiment.setParameters(request.getParameter("parameters"));
                experiment.setMetrics(request.getParameter("metrics"));
                experiment.setStatus(request.getParameter("status"));

                experimentDAO.updateExperiment(experiment);

                response.getWriter().println("Experiment updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                experimentDAO.deleteExperiment(id);

                response.getWriter().println("Experiment deleted successfully!");

            } else {

                Experiment experiment = new Experiment();

                experiment.setProjectId(Integer.parseInt(request.getParameter("projectId")));
                experiment.setDatasetId(Integer.parseInt(request.getParameter("datasetId")));
                experiment.setName(request.getParameter("name"));
                experiment.setDescription(request.getParameter("description"));
                experiment.setModelName(request.getParameter("modelName"));
                experiment.setParameters(request.getParameter("parameters"));
                experiment.setMetrics(request.getParameter("metrics"));
                experiment.setCreatedBy(Integer.parseInt(request.getParameter("createdBy")));
                experiment.setStatus("CREATED");

                experimentDAO.addExperiment(experiment);

                response.getWriter().println("Experiment created successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Experiment operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<Experiment> experiments = experimentDAO.getAllExperiments();

            if (experiments.isEmpty()) {
                response.getWriter().println("No experiments found.");
                return;
            }

            for (Experiment experiment : experiments) {
                response.getWriter().println("Experiment ID: " + experiment.getId());
                response.getWriter().println("Project ID: " + experiment.getProjectId());
                response.getWriter().println("Dataset ID: " + experiment.getDatasetId());
                response.getWriter().println("Name: " + experiment.getName());
                response.getWriter().println("Description: " + experiment.getDescription());
                response.getWriter().println("Model: " + experiment.getModelName());
                response.getWriter().println("Parameters: " + experiment.getParameters());
                response.getWriter().println("Metrics: " + experiment.getMetrics());
                response.getWriter().println("Status: " + experiment.getStatus());
                response.getWriter().println("Created By: " + experiment.getCreatedBy());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading experiments!");
        }
    }
}
