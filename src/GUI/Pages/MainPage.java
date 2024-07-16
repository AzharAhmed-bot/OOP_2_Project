package GUI.Pages;

import javax.swing.*;

import GUI.common.Navigator;
import GUI.common.Sidebar;

import java.awt.*;

public class MainPage extends JPanel {
    private Navigator navigator;
    public int userId;
    public String userName;

    public MainPage() {
        // Default constructor
    }

    public MainPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.navigator = new Navigator(); // Initialize the navigator

        // Create the sidebar
        Sidebar sidebar = new Sidebar(navigator, userId, userName);

        // Create the center message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center align content

        // Add welcome message label
        JLabel welcomeMessage = new JLabel("Hello and welcome to StudyBud! The perfect Scheduler to boost your performance!");
        messagePanel.add(welcomeMessage);

        // Add components to this JPanel
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);
        add(messagePanel, BorderLayout.CENTER);
    }

    // Main method for testing
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new MainPage(1, "John Doe")); // Example userId and userName
        frame.setVisible(true);
    }
}
