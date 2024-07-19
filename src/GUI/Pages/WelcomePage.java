package GUI.Pages;

import GUI.common.Button;
import GUI.common.Label;
import GUI.common.Navigator;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    Navigator navigator;
    SignUpPage signUpPage;
    LoginPage loginPage;
    AcademicGoalsPage academicGoalsPage;
    SubjectsPage subjectsPage;
    EnergyRatingPage energyRatingPage;
    MainPage mainPage;
    MyAcademicGoalsPage myAcademicGoalsPage;
    MySchedulePage mySchedulePage;
    MyProfilePage myProfilePage;

    public WelcomePage() {
        navigator = new Navigator();
        loginPage = new LoginPage();
        signUpPage = new SignUpPage();
        academicGoalsPage = new AcademicGoalsPage();
        subjectsPage = new SubjectsPage();
        energyRatingPage = new EnergyRatingPage();
        mainPage = new MainPage();
        myAcademicGoalsPage = new MyAcademicGoalsPage();
        mySchedulePage = new MySchedulePage();
        myProfilePage = new MyProfilePage();

        // Set background color for the main panel
        setBackground(new Color(240, 248, 255)); 
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,50));

        // Welcome message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(240, 248, 255));
        messagePanel.setLayout(new GridBagLayout());
        

        Label welcomeLabel = new Label("<html><div style='text-align: center;'>"
                + "<h1 style='font-size: 36px; color: #2E8B57;'>Welcome to StudyBud</h1>"
                + "<p style='font-size: 18px; color: #4682B4;'>Your companion to boost your study and achieve optimum performance in academics</p>"
                + "</div></html>");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(47, 79, 79));
        messagePanel.add(welcomeLabel, new GridBagConstraints());

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setLayout(new GridBagLayout());

        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);
        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);

        // Update button styles for modern look
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Rounded corners and shadows for buttons
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setOpaque(true);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        signUpButton.setOpaque(true);
        signUpButton.setBackground(new Color(34, 139, 34));
        signUpButton.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(signUpButton, gbc);

        // Add action listeners to buttons
        loginButton.addActionListener(e -> navigator.navigateToLoginPage(this));
        signUpButton.addActionListener(e -> navigator.navigateToSignUpPage(this));

        // Add panels to the main panel
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
