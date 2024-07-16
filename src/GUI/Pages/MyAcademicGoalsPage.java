package GUI.Pages;

import GUI.common.AuthenticationController;
import GUI.common.Navigator;
import GUI.common.Sidebar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Database.Models.AcademicGoal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyAcademicGoalsPage extends JPanel {
    private AuthenticationController authController;
    private int userId;
    private String userName;
    private JTable table;
    private DefaultTableModel tableModel;

    public MyAcademicGoalsPage() {
        // Default constructor
    }

    public MyAcademicGoalsPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.authController = new AuthenticationController(); // Initialize your authController here
        Navigator navigator = new Navigator();
        Sidebar sidebar = new Sidebar(navigator, userId, userName);

        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST); // Add the sidebar to the layout
        initializeComponents();
        loadAcademicGoals();
    }

    private void initializeComponents() {
        String[] columnNames = {"User", "Goal Description", "Target Date", "Priority Level", "Status", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return column != 5; // Make "Actions" column non-editable
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAcademicGoals() {
        ArrayList<AcademicGoal> academicGoals = authController.getAllAcademicGoalsPerUser(userId);
        for (AcademicGoal goal : academicGoals) {
            Object[] row = new Object[6];
            row[0] = userName;
            row[1] = goal.getGoal_description();
            row[2] = goal.getTarget_date();
            row[3] = goal.getPriority_level();
            row[4] = goal.getStatus();
            row[5] = createEditButton();
            tableModel.addRow(row);
        }
    }

    private JButton createEditButton() {
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String userName = (String) tableModel.getValueAt(row, 0);
                    String goalDescription = (String) tableModel.getValueAt(row, 1);
                    String targetDate = (String) tableModel.getValueAt(row, 2);
                    String priorityLevel = (String) tableModel.getValueAt(row, 3);
                    String status = (String) tableModel.getValueAt(row, 4);

                    System.out.println("Edited User: " + userName);
                    System.out.println("Edited Goal Description: " + goalDescription);
                    System.out.println("Edited Target Date: " + targetDate);
                    System.out.println("Edited Priority Level: " + priorityLevel);
                    System.out.println("Edited Status: " + status);
                }
            }
        });
        return editButton;
    }
}
