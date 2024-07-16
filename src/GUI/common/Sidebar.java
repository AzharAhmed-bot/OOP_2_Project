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

        setLayout(new GridLayout(4, 1)); // 4 rows, 1 column
        setBackground(Color.LIGHT_GRAY);

        // Add buttons for sidebar options
        JButton academicGoalsButton = new JButton("Academic Goals");
        JButton myScheduleButton = new JButton("My Schedule");
        JButton profileButton = new JButton("Profile");
        JButton signOutButton = new JButton("Sign Out");

        add(academicGoalsButton);
        add(myScheduleButton);
        add(profileButton);
        add(signOutButton);

        academicGoalsButton.addActionListener(createNavigationListener("AcademicGoals"));
        myScheduleButton.addActionListener(createNavigationListener("MySchedule"));
        profileButton.addActionListener(createNavigationListener("Profile"));
        signOutButton.addActionListener(createNavigationListener("SignOut"));
    }

    private ActionListener createNavigationListener(String page) {
        return e -> {
            switch (page) {
                case "AcademicGoals":
                    navigator.navigateToMyAcademicGoalsPage(this, userId, userName);
                    break;
                case "MySchedule":
                    // Add navigation to MySchedulePage
                    break;
                case "Profile":
                    // Add navigation to ProfilePage
                    break;
                case "SignOut":
                    navigator.navigateToLoginPage(this);
                    break;
            }
        };
    }
}
