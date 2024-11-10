package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddArtistGUI extends JFrame {
    private JTextField nameField;
    private JTextField genreField;
    private JTextArea bioField;
    private JTextField imageUrlField;
    private JTextField listenerCountField;
    private MongoDBHelper dbHelper;

    public AddArtistGUI() {
        dbHelper = new MongoDBHelper();
        setTitle("Add Artist");
        setSize(800, 700);  // Adjusted size for new fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 70, 70));
        JLabel titleLabel = new JLabel("Add Artist", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Artist Name
        JLabel nameLabel = new JLabel("Artist Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Genre
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        genreField = new JTextField(20);
        genreField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(genreLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(genreField, gbc);

        // Listener Count
        JLabel listenerLabel = new JLabel("Listener Count:");
        listenerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        listenerCountField = new JTextField(20);
        listenerCountField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(listenerLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(listenerCountField, gbc);

        // Bio
        JLabel bioLabel = new JLabel("Bio:");
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bioField = new JTextArea(5, 20);
        bioField.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane bioScrollPane = new JScrollPane(bioField);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(bioLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(bioScrollPane, gbc);

        // Image URL
        JLabel imageLabel = new JLabel("Image URL:");
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imageUrlField = new JTextField(20);
        imageUrlField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(imageLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(imageUrlField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        JButton addButton = new JButton("Add Artist & Bookmark");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(60, 179, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(200, 40));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String genre = genreField.getText().trim();
                String bio = bioField.getText().trim();
                String imageUrl = imageUrlField.getText().trim();
                String listenerCountText = listenerCountField.getText().trim();

                if (name.isEmpty() || genre.isEmpty() || listenerCountText.isEmpty()) {
                    JOptionPane.showMessageDialog(AddArtistGUI.this, "Name, Genre, and Listener Count are mandatory!");
                    return;
                }

                int listenerCount;
                try {
                    listenerCount = Integer.parseInt(listenerCountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddArtistGUI.this, "Listener Count must be a valid integer.");
                    return;
                }

                // Create the Artist object
                Artist artist = new Artist(name, bio, imageUrl, new String[]{genre}, listenerCount, 0, null);

                // Add artist to MongoDB
                boolean isInserted = dbHelper.insertArtist(artist);
                if (isInserted) {
                    // Add artist to bookmarks
                    BookmarkManager.addBookmark(artist);

                    // Update the BookmarkWindow with the new bookmark
                    BookmarkWindow bookmarkWindow = new BookmarkWindow();
                    bookmarkWindow.updateBookmarks();

                    JOptionPane.showMessageDialog(AddArtistGUI.this, "Artist added to database and bookmarks!");
                } else {
                    JOptionPane.showMessageDialog(AddArtistGUI.this, "Failed to add artist.");
                }
            }
        });

        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
