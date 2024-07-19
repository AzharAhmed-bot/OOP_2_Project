package GUI.Pages;

import GUI.common.AuthenticationController;
import GUI.common.Navigator;
import GUI.common.Sidebar;
import Database.Models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyProfilePage extends JPanel {
    private AuthenticationController authController;
    private int userId;
    private String userName;
    private Navigator navigator;
    private Sidebar sidebar;

    public MyProfilePage() {}

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
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 20, 20)); // Column layout
        contentPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Add cards to the content panel
        addCardsToContentPanel(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addCardsToContentPanel(JPanel contentPanel) {
        // Clear existing components
        contentPanel.removeAll();

        // Card 1: User Info
        JPanel userInfoCard = createCard("User Information", new Color(102, 204, 255)); // Light blue
        User userInfo = authController.getAllUserInfoById(userId);
        if (userInfo != null) {
            addLabel(userInfoCard, "Name: " + userInfo.getName());
            addLabel(userInfoCard, "Email: " + userInfo.getEmail());

            // Edit button
            JButton editButton = new JButton("Edit");
            editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editUserInfo();
                }
            });
            userInfoCard.add(editButton);
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

        // Refresh the view
        revalidate();
        repaint();
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

    private void editUserInfo() {
        // Fetch current user info
        User userInfo = authController.getAllUserInfoById(userId);
        if (userInfo == null) {
            JOptionPane.showMessageDialog(this, "Error fetching user information.");
            return;
        }

        // Create input fields with current values
        JTextField nameField = new JTextField(userInfo.getName());
        JTextField emailField = new JTextField(userInfo.getEmail());
        JTextField passwordField = new JTextField(userInfo.getPassword());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User Info", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Update user info
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();

            authController.updateUserInfo(userId, "name", newName);
            authController.updateUserInfo(userId, "email", newEmail);
            authController.updateUserInfo(userId, "password", newPassword);

            // Refresh user info card
            addCardsToContentPanel((JPanel) ((JScrollPane) getComponent(1)).getViewport().getView());
        }
    }
}
