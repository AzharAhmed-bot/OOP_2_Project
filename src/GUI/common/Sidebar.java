package GUI.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel {
    private Navigator navigator;
    private int userId;
    private String userName;

    public Sidebar(Navigator navigator, int userId, String userName) {
        this.navigator = navigator;
        this.userId = userId;
        this.userName = userName;

        setLayout(new GridBagLayout()); // Use GridBagLayout for more flexibility
        setBackground(new Color(41, 43, 44)); // Darker background color for modern look
        setPreferredSize(new Dimension(200, getHeight())); // Adjust width as needed

        // Create and customize buttons
        JButton academicGoalsButton = createSidebarButton("Academic Goals");
        JButton myScheduleButton = createSidebarButton("My Schedule");
        JButton profileButton = createSidebarButton("Profile");
        JButton signOutButton = createSidebarButton("Sign Out");

        // Add buttons to sidebar with padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between buttons
        add(academicGoalsButton, gbc);

        gbc.gridy++;
        add(myScheduleButton, gbc);

        gbc.gridy++;
        add(profileButton, gbc);

        gbc.gridy++;
        add(signOutButton, gbc);

        // Add action listeners to buttons
        academicGoalsButton.addActionListener(createNavigationListener("AcademicGoals"));
        myScheduleButton.addActionListener(createNavigationListener("MySchedule"));
        profileButton.addActionListener(createNavigationListener("Profile"));
        signOutButton.addActionListener(createNavigationListener("SignOut"));
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE); // White text color
        button.setBackground(new Color(56, 58, 59)); // Darker button background color
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Customize font
        return button;
    }

    private ActionListener createNavigationListener(String page) {
        return e -> {
            switch (page) {
                case "AcademicGoals":
                    navigator.navigateToMyAcademicGoalsPage(this, userId, userName);
                    break;
                case "MySchedule":
                    navigator.navigateToMySchedulesPage(this, userId, userName);
                    break;
                case "Profile":
                    navigator.navigateToMyProfilePage(this, userId, userName);
                    break;
                case "SignOut":
                    navigator.navigateToLoginPage(this);
                    break;
            }
        };
    }
}
