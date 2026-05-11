import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private JTextField txtUsername, txtSchool, txtSession;

    public ProfilePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        txtUsername = new JTextField(20);
        txtSchool = new JTextField(20);
        txtSession = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; add(txtUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("School:"), gbc);
        gbc.gridx = 1; add(txtSchool, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Session Name:"), gbc);
        gbc.gridx = 1; add(txtSession, gbc);
    }

    public boolean isComplete() {
        return !txtUsername.getText().trim().isEmpty() &&
                !txtSchool.getText().trim().isEmpty() &&
                !txtSession.getText().trim().isEmpty();
    }
}