package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Home extends JFrame {

    public Home() {
        initComponents();
    }

    private void initComponents() {
        // Setup frame properties
        setTitle("MusicMate Home");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Don't close immediately
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximized frame
        setLocationRelativeTo(null); // Center the frame

        // Add WindowListener to handle close events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });

        // Set layout and background
        setLayout(new BorderLayout());
        JPanel mainPanel = new GradientPanel(); // Custom gradient background panel
        mainPanel.setLayout(new GridBagLayout()); // Using GridBagLayout for better control

        // Set GridBag constraints for component placement
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        /// Add "MusicMate" title at the top
        JLabel titleLabel = new JLabel("MusicMate");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48)); // Increased font size to 48
        titleLabel.setForeground(new Color(255, 87, 34)); // Gold color for the title text
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 0, 40, 0); // Top spacing
        mainPanel.add(titleLabel, constraints);


        // Reset insets for other components
        constraints.insets = new Insets(10, 10, 10, 10);

        // Add the logo image below the title
        // JLabel logoLabel = createLogoLabel();
        // constraints.gridy = 1;
        // mainPanel.add(logoLabel, constraints);

        // Add slogan label below the logo
        JLabel sloganLabel = new JLabel("Discover Artists and Find Your Sound");
        sloganLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        sloganLabel.setForeground(Color.WHITE); // Always white text for visibility
        constraints.gridy = 2;
        mainPanel.add(sloganLabel, constraints);

        // Add search button centered in the middle of the screen
        JButton searchButton = new JButton("Find the artist");
        styleButton(searchButton, new Color(255, 87, 34)); // Coral color
        searchButton.addActionListener(e -> {
            new SearchWindow(); // Open Search window when clicked
        });
        constraints.gridy = 3;  // Move the button to the third row (centered)
        mainPanel.add(searchButton, constraints);

        // Create the menu bar using MenuBar class (imported)
        JMenuBar menuBar = MenuBar.createMenuBar(this);  // Using the MenuBar class created earlier
        setJMenuBar(menuBar); // Set the menu bar

        // Adding the main panel to the frame
        setContentPane(mainPanel);

        // Apply initial colors (light theme now)
        updateColors();
    }

  

    private void showExitConfirmation() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);  // Close the application if "Yes"
        }
    }

    @SuppressWarnings("unused")
    private void updateColors() {
        // Set consistent light theme colors
        Color backgroundColor = Color.WHITE;
        Color textColor = Color.BLACK;
        Color homeBarColor = new Color(0, 153, 153); // Default color for Home bar
        Color buttonColor = new Color(255, 87, 34); // Coral color for buttons

        getContentPane().setBackground(backgroundColor);
        // Repaint background and other UI elements based on the theme
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // public static void main(String[] args) {
    //     java.awt.EventQueue.invokeLater(() -> new Home().setVisible(true));
    // }

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
}
