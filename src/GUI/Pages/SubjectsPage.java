package GUI.Pages;

import Database.Models.Subject;
import GUI.common.AuthenticationController;
import GUI.common.Button;
import GUI.common.Navigator;

import javax.swing.*;
import java.awt.*;

public class SubjectsPage extends JPanel {
    private AuthenticationController authController;
    private JComboBox<Integer> subjectCountField;
    private JLabel subjectCountLabel;
    private JLabel titleLabel;
    private JPanel inputPanel;
    private int subjectCount;
    private int currentCount = 0;
    private int userId;
    private String userName;
    private Navigator navigator;

    public SubjectsPage() {}

    public SubjectsPage(int userId, String userName, int goalCount) {
        this.userId = userId;
        this.userName = userName;
        authController = new AuthenticationController();
        navigator = new Navigator();

        setBackground(new Color(240, 248, 255));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));

        // Title label
        titleLabel = new JLabel("<html><div style='text-align:center'>Welcome " + userName + ", set your subjects based on priority scale:! 😎</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(350, 50));
        centralPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Number of subjects label
        subjectCountLabel = new JLabel("Number of subjects you want to study based on priority scale:");
        subjectCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcSubjectCountLabel = new GridBagConstraints();
        gbcSubjectCountLabel.anchor = GridBagConstraints.WEST;
        gbcSubjectCountLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(subjectCountLabel, gbcSubjectCountLabel);

        // Subject count field
        subjectCountField = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            subjectCountField.addItem(i);
        }
        GridBagConstraints gbcSubjectCountField = new GridBagConstraints();
        gbcSubjectCountField.fill = GridBagConstraints.HORIZONTAL;
        gbcSubjectCountField.gridwidth = GridBagConstraints.REMAINDER;
        gbcSubjectCountField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(subjectCountField, gbcSubjectCountField);

        // Error Label
        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcErrorLabel = new GridBagConstraints();
        gbcErrorLabel.anchor = GridBagConstraints.WEST;
        gbcErrorLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(errorLabel, gbcErrorLabel);

        centralPanel.add(formPanel, BorderLayout.CENTER);

        // Input panel for new subjects
        inputPanel = new JPanel();
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        centralPanel.add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button submitButton = new Button("Add subject", new Color(70, 130, 180), Color.WHITE);
        Button cancelButton = new Button("Cancel", new Color(220, 20, 60), Color.WHITE);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            subjectCount = (int) subjectCountField.getSelectedItem();
            if (currentCount < subjectCount) {
                addSubjectInputField(errorLabel);
                currentCount++;
            }
            updateTitle();
        });

        cancelButton.addActionListener(e -> {
            currentCount = 0;
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            updateTitle();
        });

        Button prevButton = new Button("Go back", new Color(70, 130, 180), Color.WHITE);
        Button proceedButton = new Button("Proceed", new Color(34, 139, 34), Color.WHITE);
        buttonPanel.add(prevButton);
        buttonPanel.add(proceedButton);

        prevButton.addActionListener(e -> navigator.navigateToAcademicGoalPage(this, userId, userName));
        proceedButton.addActionListener(e -> navigator.navigateToEnergyRatingPage(this, userId, userName));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centralPanel, gbc);
    }

    private void addSubjectInputField(JLabel errorLabel) {
        JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subjectPanel.setBackground(new Color(240, 248, 255));
        JTextField subjectField = new JTextField(20);

        // Priority Level ComboBox
        JComboBox<Integer> priorityLevelField = new JComboBox<>();
        for (int i = 1; i <= 5; i++) {
            priorityLevelField.addItem(i);
        }

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        saveButton.addActionListener(e -> {
            String subject = subjectField.getText();
            int priorityLevel = (int) priorityLevelField.getSelectedItem();
            if (subject.isEmpty()) {
                errorLabel.setText("Subject field cannot be empty!");
            } else {
                try {
                    System.out.println(priorityLevel);
                    Subject newSubject = authController.handleSaveSubject(subject, userId, priorityLevel);
                    subjectCount = authController.getTotalSubjectsPerUser(userId);
                    newSubject.print();
                    updateTitle();
                    errorLabel.setText("");
                } finally {
                    subjectField.setText("");
                    inputPanel.remove(subjectPanel);
                    inputPanel.revalidate();
                    inputPanel.repaint();
                    currentCount--;
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(220, 20, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> {
            inputPanel.remove(subjectPanel);
            inputPanel.revalidate();
            inputPanel.repaint();
            currentCount--;
            updateTitle();
        });

        subjectPanel.add(subjectField);
        subjectPanel.add(priorityLevelField);
        subjectPanel.add(saveButton);
        subjectPanel.add(deleteButton);
        inputPanel.add(subjectPanel);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void updateTitle() {
        subjectCountLabel.setText("Number of subjects you want to study: (Current subject number: " + subjectCount + ")");
    }
}
