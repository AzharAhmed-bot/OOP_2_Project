package GUI.Pages;

import GUI.common.Button;
import GUI.common.Label;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    public WelcomePage() {
        // Set background color for the main panel
        setBackground(new Color(240, 248, 255)); // Alice Blue

        setLayout(new BorderLayout(0, 20)); // Added vertical gap between components

        // Welcome message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(240, 248, 255));
        messagePanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        Label welcomeLabel = new Label("<html><div style='text-align: center;'>"
                + "<h1>Welcome to StudyBud</h1>"
                + "<p>Your companion to boost your study and achieve optimum performance in academics</p>"
                + "</div></html>");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(47, 79, 79));
        messagePanel.add(welcomeLabel, new GridBagConstraints());

        // Buttons panel using Button class with custom colors
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);
        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between buttons

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(signUpButton, gbc);

        // Add action listeners to buttons
        loginButton.addActionListener(e -> {
            // Action when Login button is clicked
            System.out.println("Login button clicked");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            LoginPage loginPage = new LoginPage();
            frame.setContentPane(loginPage);

            // Repaint the frame
            frame.revalidate();
            frame.repaint();
        });

        signUpButton.addActionListener(e -> {
            // Action when Sign Up button is clicked
            System.out.println("Sign Up button clicked");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            SignUpPage signUpPage = new SignUpPage();
            frame.setContentPane(signUpPage);

            // Repaint the frame
            frame.revalidate();
            frame.repaint();
        });

        // Add panels to the main panel
        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
