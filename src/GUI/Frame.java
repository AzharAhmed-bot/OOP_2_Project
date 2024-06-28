package GUI;
import java.awt.*;
import javax.swing.*;

import GUI.Pages.WelcomePage;

public class Frame extends JFrame {
    public Frame(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600,400);
    


        // Panel to hold all the sections
        JPanel mainPanel=new JPanel(new BorderLayout());

        // Initialize and add the WelcomePanel
        WelcomePage welcomePage = new WelcomePage();
        mainPanel.add(welcomePage, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
}
