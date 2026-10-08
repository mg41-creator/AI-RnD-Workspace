package com.aird.servlet;

import com.aird.dao.ModelTrainingDAO;
import com.aird.model.ModelTraining;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/model-trainings")
public class ModelTrainingServlet extends HttpServlet {

    private final ModelTrainingDAO trainingDAO = new ModelTrainingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                ModelTraining training = new ModelTraining();

                training.setId(Integer.parseInt(request.getParameter("id")));
                training.setProjectId(Integer.parseInt(request.getParameter("projectId")));
                training.setDatasetId(Integer.parseInt(request.getParameter("datasetId")));
                training.setExperimentId(Integer.parseInt(request.getParameter("experimentId")));
                training.setModelName(request.getParameter("modelName"));
                training.setAlgorithm(request.getParameter("algorithm"));
                training.setParameters(request.getParameter("parameters"));
                training.setAccuracy(Double.parseDouble(request.getParameter("accuracy")));
                training.setLoss(Double.parseDouble(request.getParameter("loss")));
                training.setStatus(request.getParameter("status"));

                trainingDAO.updateTraining(training);

                response.getWriter().println("Model training updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                trainingDAO.deleteTraining(id);

                response.getWriter().println("Model training deleted successfully!");

            } else {

                ModelTraining training = new ModelTraining();

                training.setProjectId(Integer.parseInt(request.getParameter("projectId")));
                training.setDatasetId(Integer.parseInt(request.getParameter("datasetId")));
                training.setExperimentId(Integer.parseInt(request.getParameter("experimentId")));
                training.setModelName(request.getParameter("modelName"));
                training.setAlgorithm(request.getParameter("algorithm"));
                training.setParameters(request.getParameter("parameters"));
                training.setAccuracy(Double.parseDouble(request.getParameter("accuracy")));
                training.setLoss(Double.parseDouble(request.getParameter("loss")));
                training.setStartedBy(Integer.parseInt(request.getParameter("startedBy")));
                training.setStatus("QUEUED");

                trainingDAO.addTraining(training);

                response.getWriter().println("Model training created successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Model training operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<ModelTraining> trainings = trainingDAO.getAllTrainings();

            if (trainings.isEmpty()) {
                response.getWriter().println("No model trainings found.");
                return;
            }

            for (ModelTraining training : trainings) {
                response.getWriter().println("Training ID: " + training.getId());
                response.getWriter().println("Project ID: " + training.getProjectId());
                response.getWriter().println("Dataset ID: " + training.getDatasetId());
                response.getWriter().println("Experiment ID: " + training.getExperimentId());
                response.getWriter().println("Model: " + training.getModelName());
                response.getWriter().println("Algorithm: " + training.getAlgorithm());
                response.getWriter().println("Parameters: " + training.getParameters());
                response.getWriter().println("Accuracy: " + training.getAccuracy());
                response.getWriter().println("Loss: " + training.getLoss());
                response.getWriter().println("Status: " + training.getStatus());
                response.getWriter().println("Started By: " + training.getStartedBy());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading model trainings!");
        }
    }
}