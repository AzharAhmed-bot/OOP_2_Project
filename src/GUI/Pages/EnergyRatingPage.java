package GUI.Pages;

import GUI.common.Navigator;
import GUI.common.AuthenticationController;
import GUI.common.Button;
import GUI.common.Scheduler;

import javax.swing.*;


import java.awt.*;
import java.time.LocalTime;
import java.sql.Timestamp;

public class EnergyRatingPage extends JPanel {
    AuthenticationController authController;
    private JLabel errorLabel;
    private JLabel titleLabel;
    private Navigator navigator;
    private Scheduler scheduler;

    // ComboBoxes for energy levels and times
    private JComboBox<String> morningEnergyLevelComboBox;
    private JComboBox<String> afternoonEnergyLevelComboBox;
    private JComboBox<String> eveningEnergyLevelComboBox;
    private JComboBox<String> nightEnergyLevelComboBox;

    private JComboBox<LocalTime> morningTimesComboBox;
    private JComboBox<LocalTime> afternoonTimesComboBox;
    private JComboBox<LocalTime> eveningTimesComboBox;
    private JComboBox<LocalTime> nightTimesComboBox;

    public EnergyRatingPage() {
    }

    public EnergyRatingPage(int userId, String userName) {
        authController = new AuthenticationController();
        navigator = new Navigator();
        scheduler=new Scheduler(userId);
        setBackground(new Color(240, 248, 255));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(new Color(240, 248, 255));

        // Title label
        titleLabel = new JLabel("<html><div style='text-align:center'>Welcome " + userName + ", rate your energy levels during different times of the day!</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(500, 100));
        centralPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Time and energy levels
        LocalTime[] morningTimes = generateTimeOptions(6, 12);
        LocalTime[] afternoonTimes = generateTimeOptions(12, 18);
        LocalTime[] eveningTimes = generateTimeOptions(18, 21);
        LocalTime[] nightTimes = generateTimeOptions(21, 24, 6);
        String[] energyLevels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        // Labels and ComboBoxes for each time of day
        addTimeEnergyRow(formPanel, "Morning:", morningTimes, energyLevels, 0);
        addTimeEnergyRow(formPanel, "Afternoon:", afternoonTimes, energyLevels, 1);
        addTimeEnergyRow(formPanel, "Evening:", eveningTimes, energyLevels, 2);
        addTimeEnergyRow(formPanel, "Night:", nightTimes, energyLevels, 3);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        GridBagConstraints gbcErrorLabel = new GridBagConstraints();
        gbcErrorLabel.gridx = 0;
        gbcErrorLabel.gridy = 4;
        gbcErrorLabel.gridwidth = GridBagConstraints.REMAINDER;
        gbcErrorLabel.anchor = GridBagConstraints.WEST;
        gbcErrorLabel.insets = new Insets(0, 0, 10, 10);
        formPanel.add(errorLabel, gbcErrorLabel);

        centralPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        Button prevButton = new Button("Back", new Color(70, 130, 180), Color.WHITE);
        Button saveButton = new Button("Save", new Color(70, 130, 180), Color.WHITE);
        Button proceedButton = new Button("Proceed", new Color(34, 139, 34), Color.WHITE);
        buttonPanel.add(prevButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(proceedButton);

        centralPanel.add(buttonPanel, BorderLayout.SOUTH);

        prevButton.addActionListener(e -> {
            navigator.navigateToSubjectPage(this, userId, userName, userId);
        });

        saveButton.addActionListener(e -> {
            saveToDatabase(userId);
            // Print the selected times and energy ratings
            printSelectedTime("Morning", morningTimesComboBox, morningEnergyLevelComboBox);
            printSelectedTime("Afternoon", afternoonTimesComboBox, afternoonEnergyLevelComboBox);
            printSelectedTime("Evening", eveningTimesComboBox, eveningEnergyLevelComboBox);
            printSelectedTime("Night", nightTimesComboBox, nightEnergyLevelComboBox);

            // Clear error label
            errorLabel.setText("");
        });

        proceedButton.addActionListener(e -> {
            System.out.println("Navigating to next frame");
        
            // Creating a new thread for the scheduler
            Thread schedulerThread = new Thread(scheduler);
            schedulerThread.start();
           

        });
        

        // Add central panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centralPanel, gbc);
    }

    private void addTimeEnergyRow(JPanel panel, String labelText, LocalTime[] times, String[] energyLevels, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));

        JComboBox<LocalTime> timeComboBox = new JComboBox<>(times);
        JComboBox<String> energyLevelComboBox = new JComboBox<>(energyLevels);

        // Assigning the ComboBoxes to class variables for access in the saveButton ActionListener
        switch (gridy) {
            case 0:
                morningTimesComboBox = timeComboBox;
                morningEnergyLevelComboBox = energyLevelComboBox;
                break;
            case 1:
                afternoonTimesComboBox = timeComboBox;
                afternoonEnergyLevelComboBox = energyLevelComboBox;
                break;
            case 2:
                eveningTimesComboBox = timeComboBox;
                eveningEnergyLevelComboBox = energyLevelComboBox;
                break;
            case 3:
                nightTimesComboBox = timeComboBox;
                nightEnergyLevelComboBox = energyLevelComboBox;
                break;
            default:
                break;
        }

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = gridy;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(0, 0, 10, 10);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcTimeComboBox = new GridBagConstraints();
        gbcTimeComboBox.gridx = 1;
        gbcTimeComboBox.gridy = gridy;
        gbcTimeComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbcTimeComboBox.insets = new Insets(0, 0, 10, 10);
        panel.add(timeComboBox, gbcTimeComboBox);

        GridBagConstraints gbcEnergyLevelComboBox = new GridBagConstraints();
        gbcEnergyLevelComboBox.gridx = 2;
        gbcEnergyLevelComboBox.gridy = gridy;
        gbcEnergyLevelComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbcEnergyLevelComboBox.insets = new Insets(0, 0, 10, 10);
        panel.add(energyLevelComboBox, gbcEnergyLevelComboBox);
    }

    private LocalTime[] generateTimeOptions(int startHour, int endHour) {
        return generateTimeOptions(startHour, endHour, 0);
    }

    private LocalTime[] generateTimeOptions(int startHour, int endHour, int overflowHour) {
        int totalSlots = ((endHour - startHour) + (overflowHour - 0)) * 2;
        LocalTime[] timeOptions = new LocalTime[totalSlots];
        int index = 0;

        for (int hour = startHour; hour < endHour; hour++) {
            timeOptions[index++] = LocalTime.of(hour % 24, 0);
            timeOptions[index++] = LocalTime.of(hour % 24, 30);
        }

        if (overflowHour > 0) {
            for (int hour = 0; hour < overflowHour; hour++) {
                timeOptions[index++] = LocalTime.of(hour % 24, 0);
                timeOptions[index++] = LocalTime.of(hour % 24, 30);
            }
        }

        return timeOptions;
    }

    private void printSelectedTime(String timeOfDay, JComboBox<LocalTime> timeComboBox, JComboBox<String> energyLevelComboBox) {
        LocalTime selectedTime = (LocalTime) timeComboBox.getSelectedItem();
        String energyRating = (String) energyLevelComboBox.getSelectedItem();
        System.out.println(timeOfDay + " Time: " + selectedTime + ", Energy Rating: " + energyRating);
    }

    private void saveToDatabase(int userId) {
        // Save each time and energy level to the database
        saveTimeAndEnergyLevel(userId, morningTimesComboBox, morningEnergyLevelComboBox);
        saveTimeAndEnergyLevel(userId, afternoonTimesComboBox, afternoonEnergyLevelComboBox);
        saveTimeAndEnergyLevel(userId, eveningTimesComboBox, eveningEnergyLevelComboBox);
        saveTimeAndEnergyLevel(userId, nightTimesComboBox, nightEnergyLevelComboBox);

        JOptionPane.showMessageDialog(this, "Data saved successfully!", "Save Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveTimeAndEnergyLevel(int userId, JComboBox<LocalTime> timeComboBox, JComboBox<String> energyLevelComboBox) {
        LocalTime selectedTime = (LocalTime) timeComboBox.getSelectedItem();
        String energyRating = (String) energyLevelComboBox.getSelectedItem();
        
        // Example of saving to a database using your AuthenticationController (replace with your actual database handling)
        authController.handleSaveEnergyLevel(userId,selectedTime,Integer.parseInt(energyRating));
    }
}
