package com.aird.servlet;
import com.aird.dao.UserDAO;
import com.aird.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String name  = request.getParameter("name");
        String email = request.getParameter("email");
        String role  = request.getParameter("role");
        String password = request.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setRole(role);
        user.setPassword(password);
        user.setEmail(email);
        try {
            userDAO.addUser(user);
            response.setContentType("text/plain");
            response.getWriter().println("Server added succesfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Data Base Error");
        }
        //response.setContentType("text/plain");
        //response.getWriter().println("Server added succesfully");
    }
}
