package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchWindow extends JFrame {
    private final MongoDBHelper mongoHelper;
    private final ExecutorService executorService;
    private final JPanel mainPanel;

    public SearchWindow() {
        mongoHelper = new MongoDBHelper();
        executorService = Executors.newSingleThreadExecutor();

        setTitle("Search Artist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title Label
        JLabel titleLabel = new JLabel("MusicMate");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 87, 34)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 40, 0); 
        mainPanel.add(titleLabel, gbc);

        // Search Bar
        JTextField searchBar = new JTextField(20);
        styleTextField(searchBar);

        // Combo Box for Search Type
        String[] searchOptions = {"Name", "Genre"};
        JComboBox<String> searchComboBox = new JComboBox<>(searchOptions);
        styleComboBox(searchComboBox);

        // Search Button
        JButton searchButton = new JButton("Search");
        styleButton(searchButton, new Color(100, 149, 237));
        searchButton.addActionListener(e -> {
            String selectedOption = (String) searchComboBox.getSelectedItem();
            String searchTerm = searchBar.getText().trim();

            if (selectedOption != null && !searchTerm.isEmpty()) {
                if (selectedOption.equals("Name")) {
                    searchByName(searchTerm);
                } else if (selectedOption.equals("Genre")) {
                    searchByGenre(searchTerm);
                }
            } else {
                JOptionPane.showMessageDialog(SearchWindow.this, "Please enter a valid search term!");
            }
        });

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setOpaque(false);
        searchPanel.add(searchBar);
        searchPanel.add(searchComboBox);
        searchPanel.add(searchButton);

        // Add Search Panel
        gbc.gridy = 1;
        mainPanel.add(searchPanel, gbc);

        // Menu Bar
        JMenuBar menuBar = MenuBar.createMenuBar(this);
        setJMenuBar(menuBar);

        add(mainPanel);
        setVisible(true);

        // Add shutdown for executor service on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executorService.shutdownNow();
            }
        });
    }

    private void searchByName(String name) {
        executorService.submit(() -> {
            Artist foundArtist = mongoHelper.getArtistByName(name);
            SwingUtilities.invokeLater(() -> {
                if (foundArtist != null) {
                    ArrayList<Artist> artists = new ArrayList<>();
                    artists.add(foundArtist);
                    new ResultWindow(artists, name);
                } else {
                    JOptionPane.showMessageDialog(this, "Artist not found.");
                }
            });
        });
    }

    private void searchByGenre(String genre) {
        executorService.submit(() -> {
            ArrayList<Artist> foundArtists = (ArrayList<Artist>) mongoHelper.getArtistsByGenre(genre);
            SwingUtilities.invokeLater(() -> {
                if (!foundArtists.isEmpty()) {
                    new ResultWindow(foundArtists, genre);
                } else {
                    JOptionPane.showMessageDialog(this, "No artists found for this genre.");
                }
            });
        });
    }

    private void styleTextField(JTextField field) {
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(60, 60, 60));
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        field.setPreferredSize(new Dimension(250, 40));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBackground(new Color(60, 60, 60));
        comboBox.setForeground(Color.WHITE);
        comboBox.setPreferredSize(new Dimension(120, 40));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchWindow::new);
    }
}
