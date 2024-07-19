package GUI.Pages;

import GUI.common.AuthenticationController;
import GUI.common.Navigator;
import GUI.common.Sidebar;
import Database.Models.AcademicGoal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyAcademicGoalsPage extends JPanel {
    private AuthenticationController authController;
    private int userId;
    private String userName;
    private JTable table;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public MyAcademicGoalsPage() {
        // Default constructor
    }

    public MyAcademicGoalsPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.authController = new AuthenticationController();
        Navigator navigator = new Navigator();
        Sidebar sidebar = new Sidebar(navigator, userId, userName);

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST); 
        initializeComponents();
        loadAcademicGoals();
    }

    private void initializeComponents() {
        // Title
        JLabel titleLabel = new JLabel("My Academic Goals", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0x2C3E50)); // Dark blue
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table settings
        String[] columnNames = {"ID", "User", "Goal Description", "Target Date", "Priority Level", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all columns non-editable
            }
        };

        // Table customization
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(0xECF0F1)); // Light gray
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(0x3498DB)); // Blue
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Footer with user info
        JLabel footerLabel = new JLabel("Logged in as: " + userName, JLabel.CENTER);
        footerLabel.setFont(new Font("Serif", Font.ITALIC, 14));
        footerLabel.setForeground(new Color(0x7F8C8D)); // Gray
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);

        // Add double-click event listener to table
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && row != -1) {
                    editAcademicGoal(row);
                }
            }
        });
    }

    private void loadAcademicGoals() {
        ArrayList<AcademicGoal> academicGoals = authController.getAllAcademicGoalsPerUser(userId);
        for (AcademicGoal goal : academicGoals) {
            Object[] row = new Object[6];
            row[0] = goal.getId(); // Add ID to the row
            row[1] = userName;
            row[2] = goal.getGoal_description();
            row[3] = dateFormat.format(goal.getTarget_date()); // Format Date to String
            row[4] = goal.getPriority_level();
            row[5] = goal.getStatus();
            tableModel.addRow(row);
        }
    }

    private void editAcademicGoal(int row) {
        // Get the current values from the selected row
        int id = (Integer) tableModel.getValueAt(row, 0); // Get ID from the table
        String currentDescription = (String) tableModel.getValueAt(row, 2);
        String currentDate = (String) tableModel.getValueAt(row, 3);
        int currentPriority = (Integer) tableModel.getValueAt(row, 4);
        String currentStatus = (String) tableModel.getValueAt(row, 5);

        // Create input fields with current values
        JTextField descriptionField = new JTextField(currentDescription);
        JTextField dateField = new JTextField(currentDate);
        JTextField priorityField = new JTextField(String.valueOf(currentPriority)); // Convert Integer to String
        JTextField statusField = new JTextField(currentStatus);

        // Create panel for input fields
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Goal Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Target Date (yyyy-MM-dd):"));
        panel.add(dateField);
        panel.add(new JLabel("Priority Level:"));
        panel.add(priorityField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        // Customize panel
        panel.setBackground(new Color(0xF9F9F9));
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setBackground(new Color(0xFFFFFF));
                ((JTextField) comp).setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7))); 
            }
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(new Color(0x2C3E50)); 
            }
        }

        // Show input dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Academic Goal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Get new values from input fields
            String newDescription = descriptionField.getText();
            String newDate = dateField.getText();
            int newPriority = Integer.parseInt(priorityField.getText()); 
            String newStatus = statusField.getText();

            // Update the database via AuthController
            authController.updateAcademiGoal(id, "goal_description", newDescription);
            authController.updateAcademiGoal(id, "target_date", java.sql.Date.valueOf(newDate)); 
            authController.updateAcademiGoal(id, "priority_level", newPriority);
            authController.updateAcademiGoal(id, "status", newStatus);

            // Update the table with new values
            tableModel.setValueAt(newDescription, row, 2);
            tableModel.setValueAt(newDate, row, 3);
            tableModel.setValueAt(newPriority, row, 4); // Update as Integer
            tableModel.setValueAt(newStatus, row, 5);
        }
    }
}
