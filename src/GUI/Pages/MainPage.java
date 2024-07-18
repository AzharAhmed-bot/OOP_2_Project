package GUI.Pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.common.AuthenticationController;
import GUI.common.Navigator;
import GUI.common.Sidebar;
import Database.Models.*;

import java.awt.*;
import java.util.ArrayList;

public class MainPage extends JPanel {
    private Navigator navigator;
    private AuthenticationController authController;
    private int userId;
    private String userName;

    // Default constructor (not used based on your previous constructor)
    public MainPage(){}

    // Constructor with userId and userName parameters
    public MainPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.navigator = new Navigator(); // Initialize the navigator
        this.authController = new AuthenticationController();

        // Create the sidebar
        Sidebar sidebar = new Sidebar(navigator, userId, userName);

        // Create the center content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1, 20, 20));
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Card 1: Energy Levels
        JPanel energyLevelsCard = createAnalyticalCard("Energy Levels", Color.CYAN); // Light blue
        ArrayList<EnergyLevel> energyLevels = authController.getTotalScheduleTimePerUser(userId);
        if (energyLevels != null && !energyLevels.isEmpty()) {
            for (EnergyLevel energyLevel : energyLevels) {
                int energyRatingPercentage = (int) (energyLevel.getEnergy_rating() * 10);
                addLabel(energyLevelsCard, "Time of day: " + energyLevel.getTime_of_day().toString() + "hrs :  Energy Rating: " + energyRatingPercentage + "%");
            }
        } else {
            addLabel(energyLevelsCard, "No energy levels recorded.");
        }
        contentPanel.add(energyLevelsCard);

        // Card 2: Subjects
        JPanel subjectsCard = createAnalyticalCard("Subjects", Color.GREEN); // Green
        ArrayList<Subject> subjects = authController.getAllSubjectsPerUser(userId);
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) {
                int priorityPercentage = (int) (subject.getPriority_level() * 10);
                addLabel(subjectsCard, "Subject Name: " + subject.getSubject_name() + " --> Priority level: " + priorityPercentage + "%");
            }
        } else {
            addLabel(subjectsCard, "No subjects recorded.");
        }
        contentPanel.add(subjectsCard);

        // Card 3: Academic Goals
        JPanel academicGoalsCard = createAnalyticalCard("Academic Goals", Color.ORANGE); // Orange
        ArrayList<AcademicGoal> academicGoals = authController.getAllAcademicGoalsPerUser(userId);
        if (academicGoals != null && !academicGoals.isEmpty()) {
            for (AcademicGoal goal : academicGoals) {
                int goalPriorityPercentage = (int) (goal.getPriority_level() * 10);
                addLabel(academicGoalsCard, "Goal Description: " + goal.getGoal_description() + " --> Priority: " + goalPriorityPercentage + "%");
            }
        } else {
            addLabel(academicGoalsCard, "No academic goals recorded.");
        }
        contentPanel.add(academicGoalsCard);

        // Set layout for this panel
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createAnalyticalCard(String title, Color color) {
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

    private void addLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
    }
}
