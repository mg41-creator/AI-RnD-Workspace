package com.aird.dao;
import com.aird.DatabaseConnection;
import com.aird.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UserDAO {
    public void addUser(User user) throws Exception{
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?,?,?,?)";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getRole());

        ps.executeUpdate();
        ps.close();
        con.close();
    }
   public User findUserByEmail(String email) throws Exception {
       String sql = "SELECT * FROM users WHERE email = ?";
       Connection con = DatabaseConnection.getConnection();
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, email);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           User user = new User();
           user.setId(rs.getInt("id"));
           user.setName(rs.getString("name"));
           user.setEmail(rs.getString("email"));
           user.setPassword(rs.getString("password"));
           user.setRole(rs.getString("role"));
           rs.close();
           ps.close();
           con.close();

           return user;
       }
       rs.close();
       ps.close();
       con.close();
       return null;
   }
}
public User loginUser(String email, String password) throws Exception{
    String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
    Connection con = DatabaseConnection.getConnection();
    PreparedStatement ps = con.prepareStatement(sql);

    ps.setString(1, email);
    ps.setString(2, password);
    ResultSet rs = ps.executeQuery();
    if(rs.next()){
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        rs.close();
        ps.close();
        con.close();

        return user;
    }
    rs.close();
    ps.close();
    con.close();

    return null;
}
