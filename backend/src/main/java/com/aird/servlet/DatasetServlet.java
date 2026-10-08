package com.aird.servlet;

import com.aird.dao.DatasetDAO;
import com.aird.model.Dataset;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/datasets")
public class DatasetServlet extends HttpServlet {

    private final DatasetDAO datasetDAO = new DatasetDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                Dataset dataset = new Dataset();

                dataset.setId(Integer.parseInt(request.getParameter("id")));
                dataset.setName(request.getParameter("name"));
                dataset.setDescription(request.getParameter("description"));
                dataset.setFilePath(request.getParameter("filePath"));
                dataset.setFormat(request.getParameter("format"));
                dataset.setSizeMb(Double.parseDouble(request.getParameter("sizeMb")));
                dataset.setStatus(request.getParameter("status"));

                datasetDAO.updateDataset(dataset);

                response.getWriter().println("Dataset updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                datasetDAO.deleteDataset(id);

                response.getWriter().println("Dataset deleted successfully!");

            } else {

                Dataset dataset = new Dataset();

                dataset.setName(request.getParameter("name"));
                dataset.setDescription(request.getParameter("description"));
                dataset.setFilePath(request.getParameter("filePath"));
                dataset.setFormat(request.getParameter("format"));
                dataset.setSizeMb(Double.parseDouble(request.getParameter("sizeMb")));
                dataset.setUploadedBy(Integer.parseInt(request.getParameter("uploadedBy")));
                dataset.setStatus("ACTIVE");

                datasetDAO.addDataset(dataset);

                response.getWriter().println("Dataset created successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Dataset operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<Dataset> datasets = datasetDAO.getAllDatasets();

            if (datasets.isEmpty()) {
                response.getWriter().println("No datasets found.");
                return;
            }

            for (Dataset dataset : datasets) {
                response.getWriter().println("Dataset ID: " + dataset.getId());
                response.getWriter().println("Name: " + dataset.getName());
                response.getWriter().println("Description: " + dataset.getDescription());
                response.getWriter().println("File Path: " + dataset.getFilePath());
                response.getWriter().println("Format: " + dataset.getFormat());
                response.getWriter().println("Size: " + dataset.getSizeMb() + " MB");
                response.getWriter().println("Uploaded By: " + dataset.getUploadedBy());
                response.getWriter().println("Status: " + dataset.getStatus());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading datasets!");
        }
    }
}
