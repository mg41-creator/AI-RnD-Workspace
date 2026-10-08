package com.aird.servlet;

import com.aird.dao.UserDAO;
import com.aird.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        try {
            User user = userDAO.loginUser(email, password);

            response.setContentType("text/plain");

            if (user != null) {

                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userRole", user.getRole());

                response.getWriter().println("Login successful!");
                response.getWriter().println("Welcome " + user.getName());
                response.getWriter().println("Role: " + user.getRole());

                if (user.getRole().equalsIgnoreCase("ADMIN")) {
                    response.getWriter().println("Welcome to Admin Dashboard");
                } else if (user.getRole().equalsIgnoreCase("RESEARCHER")) {
                    response.getWriter().println("Welcome to Researcher Dashboard");
                }

            } else {
                response.getWriter().println("Invalid email or password!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error!");
        }
    }
}