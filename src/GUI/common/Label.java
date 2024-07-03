package GUI.common;

import javax.swing.*;
import java.awt.*;

public class Label extends JPanel {
    private JLabel label;

    public Label(String labelText) {
        setLayout(new BorderLayout());
        label = new JLabel(labelText);
        add(label, BorderLayout.CENTER);
    }

    public void setLabelText(String text) {
        label.setText(text);
    }


    public String getLabelText() {
        return label.getText();
    }

    public void setFont(Font font) {
        if (label != null) {
            label.setFont(font);
        }
    }

    public void setForeground(Color color) {
        if (label != null) {
            label.setForeground(color);
        }
    }

    public void setHorizontalAlignment(int alignment) {
        if (label != null) {
            label.setHorizontalAlignment(alignment);
        }
    }

    public void setVerticalAlignment(int alignment) {
        if (label != null) {
            label.setVerticalAlignment(alignment);
        }
    }
}
