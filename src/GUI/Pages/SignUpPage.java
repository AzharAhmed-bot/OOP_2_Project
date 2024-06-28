package GUI.Pages;

import GUI.common.Button;
import GUI.common.InputField;
import GUI.common.Label;

import javax.swing.*;

import Database.Connection.DatabaseConnection;
import Database.Models.User;
import Database.Tables.UsersTable;

import java.awt.*;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.SQLException;


public class SignUpPage extends JPanel {
    private InputField nameField;
    private InputField emailField;
    private InputField passwordField;

    public SignUpPage() {
        setBackground(new Color(240, 248, 255)); // Alice Blue
        setLayout(new BorderLayout());

        // Title label
        Label titleLabel = new Label("<html><div style='text-align: center;'>Sign Up for StudyBud</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(350, 50));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Name field
        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField = new InputField("text");
        formPanel.add(nameLabel);
        formPanel.add(nameField);

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

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);
        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Add action listener to Sign Up button
        signUpButton.addActionListener(e -> {
            // Action when Sign Up button is clicked
            String name = nameField.getInputFieldText();
            String email = emailField.getInputFieldText();
            String password = passwordField.getInputFieldText();
            Timestamp created_at = new Timestamp(System.currentTimeMillis());
            try{
                Connection connection=DatabaseConnection.getConnection();
                UsersTable usersTable=new UsersTable(connection);
                User user=new User(name,email,password,created_at);
                usersTable.insert(user);
            }catch(SQLException error){
                error.printStackTrace();;
            }finally{
                JFrame frame= (JFrame) SwingUtilities.getWindowAncestor(this);
                LoginPage loginPage=new LoginPage();
                frame.setContentPane(loginPage);
                // Repaint the frame
                frame.revalidate();
                frame.repaint();
            }

        });

        loginButton.addActionListener(e->{
            System.out.println("Login button clicked");
            JFrame frame= (JFrame) SwingUtilities.getWindowAncestor(this);
            LoginPage loginPage=new LoginPage();
            frame.setContentPane(loginPage);
            // Repaint the frame
            frame.revalidate();
            frame.repaint();
        });

        // Add components to the main panel
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
