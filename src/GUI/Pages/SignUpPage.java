package GUI.Pages;

import GUI.common.AuthenticationController;
import GUI.common.Button;
import GUI.common.InputField;
import GUI.common.Label;
import GUI.common.Navigator;

import javax.swing.*;

import Database.Models.User;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SignUpPage extends JPanel {

    private InputField nameField;
    private InputField emailField;
    private InputField passwordField;
    private AuthenticationController authenticationController;
    private Navigator navigator;

    public SignUpPage() {
        authenticationController = new AuthenticationController();
        navigator = new Navigator();
        setBackground(new Color(240, 248, 255));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a panel to center and control the width of the form
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));
        centralPanel.setPreferredSize(new Dimension(600, 500)); 

        // Title label
        Label titleLabel = new Label("<html><div style='text-align: center;'>Sign Up for StudyBud</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(350, 50));
        centralPanel.add(titleLabel, BorderLayout.NORTH);




        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // Name field
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField = new InputField("text");
        nameField.setPreferredSize(new Dimension(200, 30)); 
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        // Email field
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField = new InputField("text");
        emailField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new InputField("password");
        passwordField.setPreferredSize(new Dimension(200, 30)); 
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        
        // Error Label
        Label errorLabel = new Label("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setPreferredSize(new Dimension(400,30));
        formPanel.add(errorLabel);

        // Add form panel to central panel
        centralPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);
        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);

        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);

        // Add action listeners to buttons
        signUpButton.addActionListener(e -> {
            String name = nameField.getInputFieldText();
            String email = emailField.getInputFieldText();
            String password = passwordField.getInputFieldText();
        
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setLabelText("Please fill in all fields");
            } else {
                Timestamp created_at = new Timestamp(System.currentTimeMillis());
                try {
                    User isSignUp = authenticationController.handleSignUp(name, email, password, created_at);
                    if (isSignUp ==null) {
                        errorLabel.setLabelText("User already exists");
                    } else {
                        errorLabel.setLabelText("");
                    }
                } catch (SQLException e1) {
                    errorLabel.setLabelText("User already exists");
                    errorLabel.setLabelText("Error signing up: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        

        loginButton.addActionListener(e -> {
            navigator.navigateToLoginPage(this);
        });

        // Add button panel to central panel
        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add central panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centralPanel, gbc);
    }
}
