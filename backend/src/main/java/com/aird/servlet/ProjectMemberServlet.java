package com.aird.servlet;

import com.aird.dao.ProjectMemberDAO;
import com.aird.model.ProjectMember;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/project-members")
public class ProjectMemberServlet extends HttpServlet {

    private final ProjectMemberDAO memberDAO = new ProjectMemberDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        response.setContentType("text/plain");

        try {

            // Add member
            if ("add".equalsIgnoreCase(action)) {

                int projectId = Integer.parseInt(request.getParameter("projectId"));
                int userId = Integer.parseInt(request.getParameter("userId"));
                String role = request.getParameter("role");

                ProjectMember member = new ProjectMember();

                member.setProjectId(projectId);
                member.setUserId(userId);
                member.setRole(role);

                memberDAO.addMember(member);

                response.getWriter().println("Member added successfully!");

            }

            // Remove member
            else if ("remove".equalsIgnoreCase(action)) {

                int projectId = Integer.parseInt(request.getParameter("projectId"));
                int userId = Integer.parseInt(request.getParameter("userId"));

                memberDAO.removeMember(projectId, userId);

                response.getWriter().println("Member removed successfully!");

            }

            else {
                response.getWriter().println("Invalid action!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            response.getWriter().println("Project member operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {

            int projectId = Integer.parseInt(request.getParameter("projectId"));

            List<ProjectMember> members =
                    memberDAO.getMembersByProject(projectId);

            if (members.isEmpty()) {
                response.getWriter().println("No members found.");
                return;
            }

            for (ProjectMember member : members) {

                response.getWriter().println(
                        "Member ID: " + member.getId()
                );

                response.getWriter().println(
                        "Project ID: " + member.getProjectId()
                );

                response.getWriter().println(
                        "User ID: " + member.getUserId()
                );

                response.getWriter().println(
                        "Role: " + member.getRole()
                );

                response.getWriter().println(
                        "-------------------------"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
            response.getWriter().println("Error loading project members!");
        }
    }
}