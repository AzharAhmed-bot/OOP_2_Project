package GUI.Pages;

import Database.Models.AcademicGoal;
import GUI.common.InputField;
import GUI.common.Navigator;
import GUI.common.AuthenticationController;
import GUI.common.Button;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AcademicGoalsPage extends JPanel {
    AuthenticationController authController;
    private InputField goalDescriptionField;
    private InputField targetDateField;
    private JComboBox<Integer> priorityLevelField;
    private InputField statusField;
    private JLabel errorLabel;
    private JLabel titleLabel;
    private int goalCount;
    private Navigator navigator;

    public AcademicGoalsPage(){

    }
    public AcademicGoalsPage(int userId, String userName) {
        authController = new AuthenticationController();
        navigator=new Navigator();
        setBackground(new Color(240, 248, 255));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));
        

        // All goals per user
        goalCount=authController.getTotalGoalsPerUser(userId);
        // Title label
        titleLabel = new JLabel("<html><div style='text-align:center'>Welcome " + userName + ", set your academic goals! 😎 Current number of goals set is: "+goalCount +" </div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(350, 50));
        centralPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Goal description field
        JLabel goalDescriptionLabel = new JLabel("Goal Description:");
        goalDescriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        goalDescriptionField = new InputField("text");
        GridBagConstraints gbcGoalDescLabel = new GridBagConstraints();
        gbcGoalDescLabel.anchor = GridBagConstraints.WEST;
        gbcGoalDescLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(goalDescriptionLabel, gbcGoalDescLabel);

        GridBagConstraints gbcGoalDescField = new GridBagConstraints();
        gbcGoalDescField.fill = GridBagConstraints.HORIZONTAL;
        gbcGoalDescField.gridwidth = GridBagConstraints.REMAINDER;
        gbcGoalDescField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(goalDescriptionField, gbcGoalDescField);

        // Target date field
        JLabel targetDateLabel = new JLabel("Target Date (yyyy-MM-dd):");
        targetDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        targetDateField = new InputField("text");
        GridBagConstraints gbcTargetDateLabel = new GridBagConstraints();
        gbcTargetDateLabel.anchor = GridBagConstraints.WEST;
        gbcTargetDateLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(targetDateLabel, gbcTargetDateLabel);

        GridBagConstraints gbcTargetDateField = new GridBagConstraints();
        gbcTargetDateField.fill = GridBagConstraints.HORIZONTAL;
        gbcTargetDateField.gridwidth = GridBagConstraints.REMAINDER;
        gbcTargetDateField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(targetDateField, gbcTargetDateField);

        // Priority level field
        JLabel priorityLevelLabel = new JLabel("Priority Level:");
        priorityLevelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        priorityLevelField = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            priorityLevelField.addItem(i);
        }
        GridBagConstraints gbcPriorityLevelLabel = new GridBagConstraints();
        gbcPriorityLevelLabel.anchor = GridBagConstraints.WEST;
        gbcPriorityLevelLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(priorityLevelLabel, gbcPriorityLevelLabel);

        GridBagConstraints gbcPriorityLevelField = new GridBagConstraints();
        gbcPriorityLevelField.fill = GridBagConstraints.HORIZONTAL;
        gbcPriorityLevelField.gridwidth = GridBagConstraints.REMAINDER;
        gbcPriorityLevelField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(priorityLevelField, gbcPriorityLevelField);

        // Status field
        JLabel statusLabel = new JLabel("Status (Pending or Completed):");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusField = new InputField("text");
        GridBagConstraints gbcStatusLabel = new GridBagConstraints();
        gbcStatusLabel.anchor = GridBagConstraints.WEST;
        gbcStatusLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(statusLabel, gbcStatusLabel);

        GridBagConstraints gbcStatusField = new GridBagConstraints();
        gbcStatusField.fill = GridBagConstraints.HORIZONTAL;
        gbcStatusField.gridwidth = GridBagConstraints.REMAINDER;
        gbcStatusField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(statusField, gbcStatusField);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcErrorLabel = new GridBagConstraints();
        gbcErrorLabel.anchor = GridBagConstraints.WEST;
        gbcErrorLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(errorLabel, gbcErrorLabel);

        centralPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button saveButton = new Button("Save", new Color(70, 130, 180), Color.WHITE);
        Button proceedButton=new Button("Proceed",  new Color(34, 139, 34), Color.WHITE);
        buttonPanel.add(saveButton);
        buttonPanel.add(proceedButton);

        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String goalDescription = goalDescriptionField.getInputFieldText();
            String targetDate = targetDateField.getInputFieldText();
            int priorityLevel = (int) priorityLevelField.getSelectedItem();
            String status = statusField.getInputFieldText();
            if (goalDescription.isEmpty() || targetDate.isEmpty() || status.isEmpty()) {
                errorLabel.setText("Please fill all the fields");
            } else {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = dateFormat.parse(targetDate);
                    Date sqlDate = new Date(parsedDate.getTime());
                    AcademicGoal newGoal = authController.handleSaveGoals(userId, goalDescription, sqlDate, priorityLevel, status);
                    newGoal.printNewGoal();
                    goalCount=authController.getTotalGoalsPerUser(userId);
                    updateTitleLabel(userName);
                } catch (ParseException ex) {
                    errorLabel.setText("Invalid date format (use yyyy-MM-dd)");
                }finally{
                    errorLabel.setText("");
                    targetDateField.clearInputFieldText();
                    statusField.clearInputFieldText();
                    goalDescriptionField.clearInputFieldText();
                    priorityLevelField.setSelectedIndex(0);
                }
            }
        });

        proceedButton.addActionListener(e->{
           navigator.navigateToSubjectPage(this,userId,userName,goalCount);

        });

        // Add central panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centralPanel, gbc);
    }

    private void updateTitleLabel(String userName) {
        titleLabel.setText("<html><div style='text-align:center'>Welcome " + userName + ", set your academic goals! 😎 Current number of goals set is: " + goalCount + " </div></html>");
    }
}
