package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveArtistWindow extends JFrame {
    private JPanel artistPanel;

    public RemoveArtistWindow() {
        setTitle("Remove Artists from Bookmarks");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with vertical layout to hold all checkboxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(50, 50, 50));

        // Title label
        JLabel titleLabel = new JLabel("Select Artists to Remove:");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel to hold artist checkboxes
        artistPanel = new JPanel();
        artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
        artistPanel.setBackground(new Color(50, 50, 50));
        JScrollPane scrollPane = new JScrollPane(artistPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Load bookmarked artists as checkboxes
        loadArtistCheckboxes();

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Remove button
        JButton removeButton = new JButton("Remove Selected Artists");
        removeButton.setBackground(new Color(255, 87, 34));
        removeButton.setForeground(Color.WHITE);
        removeButton.setPreferredSize(new Dimension(200, 40));
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedArtists();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.add(removeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Load all bookmarked artists as checkboxes
    private void loadArtistCheckboxes() {
        String[] bookmarkedArtists = BookmarkManager.getBookmarkedArtistNames();
        
        if (bookmarkedArtists.length == 0) {
            JLabel noBookmarksLabel = new JLabel("No bookmarked artists.");
            noBookmarksLabel.setForeground(Color.WHITE);
            artistPanel.add(noBookmarksLabel);
        } else {
            for (String artistName : bookmarkedArtists) {
                JCheckBox artistCheckbox = new JCheckBox(artistName);
                artistCheckbox.setForeground(Color.WHITE);
                artistCheckbox.setBackground(new Color(50, 50, 50));
                artistPanel.add(artistCheckbox);
            }
        }
    }

    // Remove selected artists from bookmarks
    private void removeSelectedArtists() {
        ArrayList<String> selectedArtists = new ArrayList<>();

        // Iterate through all components in the artistPanel
        for (Component comp : artistPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    selectedArtists.add(checkBox.getText());
                }
            }
        }

        if (selectedArtists.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No artists selected!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Remove selected artists from BookmarkManager
            for (String artistName : selectedArtists) {
                BookmarkManager.removeBookmark(artistName);
            }
            JOptionPane.showMessageDialog(this, "Selected artists removed from bookmarks.");

            // Refresh the checkboxes
            artistPanel.removeAll();
            loadArtistCheckboxes();
            artistPanel.revalidate();
            artistPanel.repaint();
        }
    }
}
