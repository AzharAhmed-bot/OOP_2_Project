package GUI.Pages;

import GUI.common.Button;
import GUI.common.Label;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel {
    public WelcomePage() {
        // Set background color for the main panel
        setBackground(new Color(240, 248, 255)); // Alice Blue

        setLayout(new BorderLayout());

        // Welcome message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(240, 248, 255)); 

        Label welcomeLabel = new Label("<html><div style='text-align: center;'>"
                + "Welcome to StudyBud,<br>"
                + "your companion to boost your study<br>"
                + "and achieve optimum performance in academics"
                + "</div></html>");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setForeground(new Color(47, 79, 79));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setPreferredSize(new Dimension(350, 100));
        messagePanel.add(welcomeLabel);

        // Buttons panel using Button class with custom colors
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button loginButton = new Button("Login", new Color(70, 130, 180), Color.WHITE);
        Button signUpButton = new Button("Sign Up", new Color(34, 139, 34), Color.WHITE);

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Add action listeners to buttons
        loginButton.addActionListener(e -> {
            // Action when Login button is clicked
            System.out.println("Login button clicked");
            JFrame frame= (JFrame) SwingUtilities.getWindowAncestor(this);
            LoginPage loginPage=new LoginPage();
            frame.setContentPane(loginPage);

            // Repaint the frame
            frame.revalidate();
            frame.repaint();
        });

        signUpButton.addActionListener(e -> {
            // Action when Sign Up button is clicked
            System.out.println("Sign Up button clicked");
            JFrame frame= (JFrame) SwingUtilities.getWindowAncestor(this);
            SignUpPage signUpPage=new SignUpPage();
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
