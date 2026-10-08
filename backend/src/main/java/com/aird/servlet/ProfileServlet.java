package com.aird.servlet;

import com.aird.dao.ProfileDAO;
import com.aird.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private final ProfileDAO profileDAO = new ProfileDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            if ("password".equalsIgnoreCase(action)) {

                String password = request.getParameter("password");

                profileDAO.updatePassword(userId, password);

                response.getWriter().println("Password updated successfully!");

            } else {

                User user = new User();

                user.setId(userId);
                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));

                profileDAO.updateProfile(user);

                response.getWriter().println("Profile updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Profile operation failed!");
        }
    }
}