/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0660563
 */
@ApplicationScoped
@ManagedBean
public class UserController {
    
    private List<User> users;
    private static UserController instance;

    public UserController() {
        updateUsersFromDatabase();
        instance = this;
    }

    public List<User> getUsers() {
        return users;
    }
    
    public static UserController getInstance() {
        return instance;
    }
    
    public String getUsernameById(int id) {
        for(User u: users) {
            if(u.getId() == id)
                return u.getUsername();
        }
        return null;
    }
    
    public int getUserIdByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username))
                return u.getId();
        }
        return -1;
    }
    
    private void updateUsersFromDatabase() {
        try {
            //users.clear();
            users = new ArrayList<>();
            
            //Make a Connection
            Connection conn = Utils.getConnection();
            
            //Build a Query
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            //Parse the Results
            while(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("passhash")
                );
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            users = new ArrayList<>();
        }
    }
    
    public String addUser(String username, String password) {
        try (Connection conn = Utils.getConnection()) {
            String passhash = Utils.hash(password);
            String sql = "INSERT INTO users (username, passhash) VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, passhash);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateUsersFromDatabase();
        return "index";
    }
    
}
