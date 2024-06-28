package GUI.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JPanel {
    private JButton button;

    public Button(String buttonText, Color background, Color foreground) {
        setLayout(new BorderLayout());
        button = new JButton(buttonText);
        button.setBackground(background);
        button.setForeground(foreground);
        add(button, BorderLayout.CENTER);
    }

    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public String getButtonText() {
        return button.getText();
    }
}
