package com.aird;
import com.aird.dao.UserDAO;
import com.aird.model.User;

import java.sql.Connection;
public class Main {
    static void main(String[] args) {
        try{
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByEmail("test123@email.com");
            if(user != null){
                System.out.println("User Found");
                System.out.println("Name: "+user.getName());
                System.out.println("Email: "+user.getEmail());
                System.out.println("Role: "+user.getRole());
            }else{
                System.out.println("User Not Found !!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

