package com.aird.servlet;

import com.aird.dao.CollaborationDAO;
import com.aird.model.Collaboration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/collaborations")
public class CollaborationServlet extends HttpServlet {

    private final CollaborationDAO collaborationDAO = new CollaborationDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                Collaboration collaboration = new Collaboration();

                collaboration.setId(Integer.parseInt(request.getParameter("id")));
                collaboration.setMessage(request.getParameter("message"));
                collaboration.setType(request.getParameter("type"));

                collaborationDAO.updateCollaboration(collaboration);

                response.getWriter().println("Collaboration updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                collaborationDAO.deleteCollaboration(id);

                response.getWriter().println("Collaboration deleted successfully!");

            } else {

                Collaboration collaboration = new Collaboration();

                collaboration.setProjectId(Integer.parseInt(request.getParameter("projectId")));
                collaboration.setUserId(Integer.parseInt(request.getParameter("userId")));
                collaboration.setMessage(request.getParameter("message"));
                collaboration.setType(request.getParameter("type"));

                collaborationDAO.addCollaboration(collaboration);

                response.getWriter().println("Collaboration added successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Collaboration operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            int projectId = Integer.parseInt(request.getParameter("projectId"));

            List<Collaboration> collaborations =
                    collaborationDAO.getByProject(projectId);

            if (collaborations.isEmpty()) {
                response.getWriter().println("No collaborations found.");
                return;
            }

            for (Collaboration collaboration : collaborations) {
                response.getWriter().println("ID: " + collaboration.getId());
                response.getWriter().println("Project ID: " + collaboration.getProjectId());
                response.getWriter().println("User ID: " + collaboration.getUserId());
                response.getWriter().println("Message: " + collaboration.getMessage());
                response.getWriter().println("Type: " + collaboration.getType());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading collaborations!");
        }
    }
}
