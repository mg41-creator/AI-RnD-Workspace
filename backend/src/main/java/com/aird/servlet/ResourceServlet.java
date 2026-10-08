package com.aird.servlet;

import com.aird.dao.ResourceDAO;
import com.aird.model.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/resources")
public class ResourceServlet extends HttpServlet {

    private final ResourceDAO resourceDAO = new ResourceDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                Resource resource = new Resource();

                resource.setId(Integer.parseInt(request.getParameter("id")));
                resource.setName(request.getParameter("name"));
                resource.setType(request.getParameter("type"));
                resource.setDescription(request.getParameter("description"));
                resource.setLocation(request.getParameter("location"));
                resource.setStatus(request.getParameter("status"));

                resourceDAO.updateResource(resource);

                response.getWriter().println("Resource updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                resourceDAO.deleteResource(id);

                response.getWriter().println("Resource deleted successfully!");

            } else {

                Resource resource = new Resource();

                resource.setName(request.getParameter("name"));
                resource.setType(request.getParameter("type"));
                resource.setDescription(request.getParameter("description"));
                resource.setLocation(request.getParameter("location"));
                resource.setStatus("AVAILABLE");

                resourceDAO.addResource(resource);

                response.getWriter().println("Resource created successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Resource operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<Resource> resources = resourceDAO.getAllResources();

            if (resources.isEmpty()) {
                response.getWriter().println("No resources found.");
                return;
            }

            for (Resource resource : resources) {
                response.getWriter().println("Resource ID: " + resource.getId());
                response.getWriter().println("Name: " + resource.getName());
                response.getWriter().println("Type: " + resource.getType());
                response.getWriter().println("Description: " + resource.getDescription());
                response.getWriter().println("Location: " + resource.getLocation());
                response.getWriter().println("Status: " + resource.getStatus());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading resources!");
        }
    }
}