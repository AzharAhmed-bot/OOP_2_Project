package GUI.Pages;

import GUI.common.AuthenticationController;
import GUI.common.Navigator;
import GUI.common.Sidebar;
import Database.Models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MyProfilePage extends JPanel {
    private AuthenticationController authController;
    private int userId;
    private String userName;
    private Navigator navigator;
    private Sidebar sidebar;

    public MyProfilePage(){}
    public MyProfilePage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.authController = new AuthenticationController();
        this.navigator = new Navigator();
        this.sidebar = new Sidebar(navigator, userId, userName);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        // Sidebar
        add(sidebar, BorderLayout.WEST);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Card 1: User Info
        JPanel userInfoCard = createCard("User Information", new Color(102, 204, 255)); // Light blue
        User userInfo = authController.getAllUserInfoById(userId);
        if (userInfo != null) {
            addLabel(userInfoCard, "Name: " + userInfo.getName());
            addLabel(userInfoCard, "Email: " + userInfo.getEmail());
        } else {
            addLabel(userInfoCard, "Error fetching user information.");
        }
        contentPanel.add(userInfoCard);

        // Card 2: Subjects, Energy Levels, and Goals
        JPanel subjectsEnergyGoalsCard = createCard("Subjects, Energy Levels, and Goals", new Color(255, 187, 51)); // Orange
        int subjects = authController.getTotalSubjectsPerUser(userId);
        JLabel subjectsLabel = addLabel(subjectsEnergyGoalsCard, "Total Subjects Enrolled: " + subjects);

        ArrayList<EnergyLevel> totalEnergyLevels = authController.getTotalScheduleTimePerUser(userId);
        JLabel energyLevelsLabel = addLabel(subjectsEnergyGoalsCard, "Total Energy Levels: " + totalEnergyLevels.size());

        int totalGoals = authController.getTotalGoalsPerUser(userId);
        addLabel(subjectsEnergyGoalsCard, "Total Academic Goals: " + totalGoals);

        contentPanel.add(subjectsEnergyGoalsCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, Color color) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createLineBorder(color, 2)); // Colored border
        cardPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        cardPanel.add(titleLabel);

        return cardPanel;
    }

    private JLabel addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        return label;
    }
}
