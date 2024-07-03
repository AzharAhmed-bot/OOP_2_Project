package GUI.Pages;

import javax.swing.*;
import java.awt.*;

public class AcademicGoalsPage extends JPanel {
    private JTextField goalTextField;
    private int userId;
    public AcademicGoalsPage(int userId){
        this.userId=userId;
        setBackground(new Color(240, 248, 255)); 
        setLayout(new BorderLayout()); 
    }

    
}
