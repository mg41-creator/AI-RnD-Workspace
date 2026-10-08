package com.aird.servlet;

import com.aird.dao.ProjectDAO;
import com.aird.model.Project;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {

    private final ProjectDAO projectDAO = new ProjectDAO();

    // Create a project
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        response.setContentType("text/plain");

        try {

            // Update project
            if ("update".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String status = request.getParameter("status");

                Project project = new Project();

                project.setId(id);
                project.setTitle(title);
                project.setDescription(description);
                project.setStatus(status);

                projectDAO.updateProject(project);

                response.getWriter().println("Project updated successfully!");

            }

            // Delete project
            else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                projectDAO.deleteProject(id);

                response.getWriter().println("Project deleted successfully!");

            }

            // Create project
            else {

                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String createdBy = request.getParameter("createdBy");

                Project project = new Project();

                project.setTitle(title);
                project.setDescription(description);
                project.setCreatedBy(Integer.parseInt(createdBy));
                project.setStatus("ACTIVE");

                projectDAO.addProject(project);

                response.getWriter().println("Project created successfully!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            response.getWriter().println("Project operation failed!");
        }
    }
    // View all projects
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<Project> projects = projectDAO.getAllProjects();

            if (projects.isEmpty()) {
                response.getWriter().println("No projects found.");
                return;
            }

            for (Project project : projects) {

                response.getWriter().println("Project ID: " + project.getId());
                response.getWriter().println("Title: " + project.getTitle());
                response.getWriter().println("Description: " + project.getDescription());
                response.getWriter().println("Created By: " + project.getCreatedBy());
                response.getWriter().println("Status: " + project.getStatus());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading projects!");
        }
    }
}