package GUI.common;

import javax.swing.*;
import java.awt.*;

public class InputField extends JPanel {
    private final JTextField inputField;

    public InputField(String type) {
        setLayout(new BorderLayout());
        if ("text".equals(type)) {
            inputField = new JTextField();
        } else {
            inputField = new JPasswordField();
        }
        add(inputField);
    }

    public String getInputFieldText() {
        return inputField.getText();
    }
}
