package GUI.common;


import javax.swing.*;

import Database.Models.AcademicGoal;
import Database.Models.User;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AuthenticationController {
    private AuthenticationService authService;


    public AuthenticationController() {
        authService = new AuthenticationService();
        
    }

    public User handleSignUp(String name, String email, String password, Timestamp createdAt) throws SQLException {
        User newUser = authService.newUser(name, email, password, createdAt);
        if (newUser != null) {
            System.out.println("User created: " + newUser.getName());
            return newUser;
        }
        return null;
    }
    

    public User handleLogin(String email, String password) {
        try {
            User user = authService.authenticate(email, password);
            if (user != null) {
                System.out.println("Login successful, welcome " + user.getName());
                return user;
                // Navigate to main application page or perform other actions
            } else {
                System.out.println("Login failed");
                return null;
            }
        } catch (SQLException e ) {
            JOptionPane.showMessageDialog(null, "Error authenticating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public AcademicGoal handleSaveGoals(int userId,String goal_description,Date target_date,int priority_level,String status){
        AcademicGoal newAcademicGoal=authService.newAcademicGoal(userId, goal_description, target_date, priority_level, status);
        if (newAcademicGoal != null) {
            return newAcademicGoal;
        }
        return null;
    }
  
    
}
