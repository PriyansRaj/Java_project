package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class SignupGUI extends JFrame {

    private JTextField signupUsernameField;
    private JPasswordField signupPasswordField;
    private JButton signupSubmitButton;
    private JLabel signupMessageLabel;
    private Map<String, Login.User> userCredentials;
    private Login loginGUI;

    public SignupGUI(Map<String, Login.User> userCredentials, Login loginGUI) {
        this.userCredentials = userCredentials;
        this.loginGUI = loginGUI;

        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        // Username label and field
        JLabel signupUsernameLabel = new JLabel("Username:");
        signupUsernameLabel.setForeground(Color.WHITE);
        signupUsernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        signupUsernameField = new JTextField(20);
        styleTextField(signupUsernameField);

        // Password label and field
        JLabel signupPasswordLabel = new JLabel("Password:");
        signupPasswordLabel.setForeground(Color.WHITE);
        signupPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        signupPasswordField = new JPasswordField(20);
        styleTextField(signupPasswordField);

        // Submit button styling
        signupSubmitButton = new JButton("Submit");
        styleButton(signupSubmitButton, new Color(144, 238, 144)); // Light green color
        signupSubmitButton.addActionListener(e -> handleSignup());

        // Signup message label
        signupMessageLabel = new JLabel();
        signupMessageLabel.setForeground(Color.LIGHT_GRAY);
        signupMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout components
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(signupUsernameLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(signupUsernameField, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        mainPanel.add(signupPasswordLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(signupPasswordField, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.gridwidth = 2; // Span both columns
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(signupSubmitButton, constraints);

        constraints.gridy = 3;
        mainPanel.add(signupMessageLabel, constraints);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleSignup() {
        String username = signupUsernameField.getText();
        String password = new String(signupPasswordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            if (!userCredentials.containsKey(username)) { // Check for existing username
                Login.User user = new Login.User(username, password);
                userCredentials.put(username, user);
                saveUserCredentials();
                signupMessageLabel.setText("Signup successful! You can now login.");
                signupMessageLabel.setForeground(Color.GREEN);  // Change text color to green for success

                // Wait for the user to read the success message before closing the signup window
                new Timer(2000, e -> {
                    dispose();  // Close the signup window
                    // Show the login window and bring it to the front
                    loginGUI.setVisible(true);
                    loginGUI.toFront();  // Ensure the login window comes to the front
                }).start(); // Timer to delay window switch for a brief moment
            } else {
                signupMessageLabel.setText("Username already exists. Please choose a different one.");
                signupMessageLabel.setForeground(Color.RED);  // Change text color to red for error
            }
        } else {
            signupMessageLabel.setText("Please enter valid username and password.");
            signupMessageLabel.setForeground(Color.RED);  // Change text color to red for error
        }
    }

    private void saveUserCredentials() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_credentials.txt"))) {
            oos.writeObject(userCredentials);
        } catch (IOException e) {
            System.out.println("Error saving user credentials: " + e.getMessage());
        }
    }

    // Panel with gradient background
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(58, 123, 213); // Start color
            Color color2 = new Color(255, 0, 150); // End color
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // TextField styling
    private void styleTextField(JTextField field) {
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
    }

    // Button styling
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true)); // Rounded border
    }
}
