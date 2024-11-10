package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private JCheckBox rememberMeCheckBox;
    private JLabel messageLabel;
    private Map<String, User> userCredentials;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Set layout and add a panel with a gradient background
        setLayout(new BorderLayout());
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        // GridBagConstraints for flexible layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        // Add the MusicMate text as a label
        JLabel logoLabel = new JLabel("MusicMate", JLabel.CENTER);  // Text-based logo
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));  // Large, bold font
        logoLabel.setForeground(new Color(255, 87, 34));  // Coral color
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // Span across two columns to center align
        mainPanel.add(logoLabel, constraints);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridy = 1;
        constraints.gridwidth = 1; // Reset grid width
        mainPanel.add(usernameLabel, constraints);

        // Username Text Field
        usernameField = new JTextField(20);
        styleTextField(usernameField, "Username");
        constraints.gridy = 2;
        mainPanel.add(usernameField, constraints);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        constraints.gridy = 3;
        mainPanel.add(passwordLabel, constraints);

        // Password Text Field
        passwordField = new JPasswordField(20);
        styleTextField(passwordField, "Password");
        constraints.gridy = 4;
        mainPanel.add(passwordField, constraints);

        // Login Button styling
        loginButton = new JButton("LOGIN");
        styleButton(loginButton, new Color(255, 87, 34)); // Coral color
        loginButton.addActionListener(e -> handleLogin());
        constraints.gridy = 5;
        mainPanel.add(loginButton, constraints);

        // Signup Button styling
        signupButton = new JButton("SIGNUP");
        styleButton(signupButton, new Color(58, 123, 213)); // Blue color
        signupButton.addActionListener(e -> new SignupGUI(userCredentials, this));
        constraints.gridy = 6;
        mainPanel.add(signupButton, constraints);

        // Remember Me checkbox
        rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setForeground(Color.WHITE);
        rememberMeCheckBox.setOpaque(false);
        constraints.gridy = 7;
        mainPanel.add(rememberMeCheckBox, constraints);

        // Forgot Password label
        messageLabel = new JLabel("Forgot your password?");
        messageLabel.setForeground(Color.LIGHT_GRAY);
        constraints.gridy = 8;
        mainPanel.add(messageLabel, constraints);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);

        loadUserCredentials();  // Load user credentials from the file
    }

    // Panel with gradient background
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(58, 123, 213); 
            Color color2 = new Color(255, 0, 150);
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        field.setToolTipText(placeholder);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
    }

    @SuppressWarnings("unchecked")
    private void loadUserCredentials() {
        userCredentials = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_credentials.txt"))) {
            userCredentials = (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            if (!(e instanceof FileNotFoundException)) {
                System.out.println("Error loading user credentials: " + e.getMessage());
            }
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username or password cannot be empty.");
            return;
        }
    
        // Validate login credentials
        if (userCredentials.containsKey(username)) {
            User user = userCredentials.get(username);
            if (user.password.equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    
                // Dispose the Login window and open the Home on the EDT (Event Dispatch Thread)
                SwingUtilities.invokeLater(() -> {
                    dispose();  // Close the Login window
                    new Home().setVisible(true);  // Open the Home window
                });
            } else {
                messageLabel.setText("Incorrect password. Please try again.");
            }
        } else {
            messageLabel.setText("Username not found. Please sign up.");
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        String username;
        String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());  // Start the login GUI
    }
}
