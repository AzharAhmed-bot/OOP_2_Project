package GUI.Pages;

import javax.swing.*;
import java.awt.*;
import Database.Models.Subject;
import GUI.common.AuthenticationController;
import GUI.common.*;
import GUI.common.Button;

public class SubjectsPage extends JPanel {
    private JComboBox<Integer> subjectCountField;
    private AuthenticationController authController;
    private int subjectCount;
    private int currentCount = 0;
    private JPanel inputPanel;
    private JButton submitButton;
    private JButton cancelButton;
    private int userId;
    private String userName;
    private JLabel subjectCountLabel;
    private Navigator navigator;

    public SubjectsPage() {}

    public SubjectsPage(int userId, String userName, int goalCount) {
        this.userId = userId;
        this.userName = userName;
        authController = new AuthenticationController();
        navigator=new Navigator();

        setBackground(new Color(240, 248, 255));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));

        // Number of subjects
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Introduction label
        subjectCountLabel = new JLabel("Number of subjects you want to study:(Current subject number "+subjectCount+"):");
        subjectCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcSubjectCountLabel = new GridBagConstraints();
        gbcSubjectCountLabel.anchor = GridBagConstraints.WEST;
        gbcSubjectCountLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(subjectCountLabel, gbcSubjectCountLabel);

        // Total subjects Label
        subjectCountField = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            subjectCountField.addItem(i);
        }
        GridBagConstraints gbcSubjectCountField = new GridBagConstraints();
        gbcSubjectCountField.fill = GridBagConstraints.HORIZONTAL;
        gbcSubjectCountField.insets = new Insets(0, 0, 10, 10);
        formPanel.add(subjectCountField, gbcSubjectCountField);

        // Save button 
        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcSubmitButton = new GridBagConstraints();
        gbcSubmitButton.gridwidth = GridBagConstraints.REMAINDER;
        gbcSubmitButton.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbcSubmitButton);

        centralPanel.add(formPanel, BorderLayout.NORTH);

        // Adding new Subject input
        inputPanel = new JPanel();
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        centralPanel.add(inputPanel, BorderLayout.CENTER);

        // Cancel button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(cancelButton);

        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            subjectCount = (int) subjectCountField.getSelectedItem();
            if (currentCount < subjectCount) {
                addSubjectInputField();
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

        prevButton.addActionListener(e->{
            navigator.navigateToAcademicGoalPage(this,userId,userName);
        });

        proceedButton.addActionListener(e->{
            System.out.println("Proceed to next frame!");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centralPanel, gbc);
    }

    private void addSubjectInputField() {
        JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subjectPanel.setBackground(new Color(240, 248, 255));
        JTextField subjectField = new JTextField(20);
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            String subject = subjectField.getText();
            if (subject.isEmpty()) {
                System.err.println("Empty field!");
            } else {
                try {
                    Subject newSubject = authController.handleSaveSubject(subject, userId);
                    subjectCount = authController.getTotalSubjectsPerUser(userId);
                    newSubject.print();
                    updateTitle();
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
        subjectPanel.add(saveButton);
        subjectPanel.add(deleteButton);
        inputPanel.add(subjectPanel);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void updateTitle() {
        subjectCountLabel.setText("Number of subjects you want to study:Current subject number"+subjectCount+ "):");
    }
}
