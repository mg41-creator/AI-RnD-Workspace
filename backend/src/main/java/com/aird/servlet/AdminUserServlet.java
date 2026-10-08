package com.aird.servlet;

import com.aird.dao.UserDAO;
import com.aird.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/plain");

        try {
            if ("update".equalsIgnoreCase(action)) {

                User user = new User();

                user.setId(Integer.parseInt(request.getParameter("id")));
                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));
                user.setRole(request.getParameter("role"));

                userDAO.updateUser(user);

                response.getWriter().println("User updated successfully!");

            } else if ("delete".equalsIgnoreCase(action)) {

                int id = Integer.parseInt(request.getParameter("id"));

                userDAO.deleteUser(id);

                response.getWriter().println("User deleted successfully!");

            } else {

                User user = new User();

                user.setName(request.getParameter("name"));
                user.setEmail(request.getParameter("email"));
                user.setPassword(request.getParameter("password"));
                user.setRole(request.getParameter("role"));

                userDAO.addUser(user);

                response.getWriter().println("User created successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("User operation failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {
            List<User> users = userDAO.getAllUsers();

            if (users.isEmpty()) {
                response.getWriter().println("No users found.");
                return;
            }

            for (User user : users) {
                response.getWriter().println("User ID: " + user.getId());
                response.getWriter().println("Name: " + user.getName());
                response.getWriter().println("Email: " + user.getEmail());
                response.getWriter().println("Role: " + user.getRole());
                response.getWriter().println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error loading users!");
        }
    }
}
