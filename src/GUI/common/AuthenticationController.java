package GUI.common;


import javax.swing.*;
import Database.Models.User;


import java.sql.SQLException;
import java.sql.Timestamp;

public class AuthenticationController {
    private AuthenticationService authService;

    public AuthenticationController() {
        authService = new AuthenticationService();

    }

    public void handleSignUp(String name, String email, String password, Timestamp createdAt) {
        User newUser = authService.newUser(name, email, password, createdAt);
        System.out.println("User created: " + newUser.getName());
    }

    public void handleLogin(String email, String password) {
        try {
            User user = authService.authenticate(email, password);
            if (user != null) {
                System.out.println("Login successful, welcome " + user.getName());
                // Navigate to main application page or perform other actions
            } else {
                System.out.println("Login failed");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error authenticating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
  
    
}
