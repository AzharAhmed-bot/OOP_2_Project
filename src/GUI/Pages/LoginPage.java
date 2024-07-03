package GUI.Pages;

import GUI.common.AuthenticationController;

import GUI.common.Button;
import GUI.common.InputField;
import GUI.common.Label;
import GUI.common.Navigator;

import javax.swing.*;

import Database.Models.User;

import java.awt.*;

public class LoginPage extends JPanel {

    private InputField emailField;
    private InputField passwordField;
    private AuthenticationController authController;
    private Navigator navigator;

    public LoginPage() {
        authController = new AuthenticationController();
        navigator=new Navigator();
        setBackground(new Color(240, 248, 255)); 
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a panel to center and control the width of the form
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));
        centralPanel.setPreferredSize(new Dimension(400, 300)); // Adjust size as needed

        // Title label
        Label titleLabel = new Label("<html><div style='text-align: center;'>Login to StudyBud</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(350, 50));
        centralPanel.add(titleLabel, BorderLayout.NORTH);


        
        



        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        // Email field
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField = new InputField("text");
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new InputField("password");
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Error Label
        Label errorLabel=new Label("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVerticalAlignment(SwingConstants.CENTER);
        formPanel.add(errorLabel);

        // Add form panel to central panel
        centralPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);
        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);
    

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Add action listeners to buttons
        loginButton.addActionListener(e -> {
            String email = emailField.getInputFieldText();
            String password = passwordField.getInputFieldText();
            
            if(email.isEmpty() || password.isEmpty()){
                errorLabel.setLabelText("Please fill in the fields ");
            }else{
                User isLoggedIn=authController.handleLogin(email, password);
            
                if(isLoggedIn ==null){
                    errorLabel.setLabelText("Invalid email or password. Please try again.");
                }else{
                    errorLabel.setLabelText("Login successful");
                    navigator.navigateToAcademicGoalPage(this,isLoggedIn.getId(),isLoggedIn.getName());
                }
            }
        });

        signUpButton.addActionListener(e -> {
            // Action when Sign Up button is clicked
            navigator.navigateToSignUpPage(this);
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
