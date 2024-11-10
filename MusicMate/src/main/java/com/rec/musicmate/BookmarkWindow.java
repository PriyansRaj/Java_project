package com.rec.musicmate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookmarkWindow extends JFrame {
    private JPanel panel;

    public BookmarkWindow() {
        setTitle("Bookmarked Artists");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main container with title and scrollable panel
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(40, 40, 40));

        // Title label
        JLabel titleLabel = new JLabel("Bookmarked Artists", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));  // Padding around the title
        mainContainer.add(titleLabel, BorderLayout.NORTH);

        // Panel to hold all bookmarked artists
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);  // Remove border for cleaner look
        mainContainer.add(scrollPane, BorderLayout.CENTER);

        add(mainContainer);

        updateBookmarks();

        setVisible(true);
    }

    // Method to update the displayed bookmarks
    public void updateBookmarks() {
        panel.removeAll();  // Clear the panel before adding updated list of bookmarks

        List<Artist> bookmarkedArtists = BookmarkManager.getBookmarkedArtists();

        if (bookmarkedArtists.isEmpty()) {
            JLabel noBookmarksLabel = new JLabel("No bookmarked artists.");
            noBookmarksLabel.setForeground(Color.WHITE);
            noBookmarksLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            panel.add(noBookmarksLabel);
        } else {
            for (Artist artist : bookmarkedArtists) {
                // Artist card panel with rounded borders
                JPanel artistCardPanel = new JPanel(new BorderLayout());
                artistCardPanel.setBackground(new Color(55, 55, 55));
                artistCardPanel.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(100, 100, 100), 1, true),  // Rounded border
                        new EmptyBorder(10, 15, 10, 15)));  // Inner padding
                artistCardPanel.setPreferredSize(new Dimension(800, 150));

                // Top section: Artist Name and Listener Count
                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setBackground(new Color(55, 55, 55));

                JLabel artistNameLabel = new JLabel("Name: " + artist.getName());
                artistNameLabel.setForeground(Color.WHITE);
                artistNameLabel.setFont(new Font("Arial", Font.BOLD, 16));

                JLabel listenerCountLabel = new JLabel("Listeners: " + artist.getListeners());
                listenerCountLabel.setForeground(new Color(135, 206, 250));  // Lighter blue for visibility
                listenerCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
                listenerCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                topPanel.add(artistNameLabel, BorderLayout.WEST);
                topPanel.add(listenerCountLabel, BorderLayout.EAST);
                artistCardPanel.add(topPanel, BorderLayout.NORTH);

                // Middle section: Artist Genre(s)
                String genres = String.join(", ", artist.getTags());  // Assuming artist has a getTags() method
                JLabel artistGenreLabel = new JLabel("Genre: " + genres);
                artistGenreLabel.setForeground(Color.LIGHT_GRAY);
                artistGenreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                artistGenreLabel.setBorder(new EmptyBorder(10, 0, 10, 0));  // Padding around the genre label
                artistCardPanel.add(artistGenreLabel, BorderLayout.CENTER);

                // Bottom section: Remove Button
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                bottomPanel.setBackground(new Color(55, 55, 55));

                JButton removeButton = new JButton("Remove");
                removeButton.setBackground(new Color(255, 87, 34));  // Orange color for remove button
                removeButton.setForeground(Color.WHITE);
                removeButton.setFont(new Font("Arial", Font.BOLD, 12));
                removeButton.setPreferredSize(new Dimension(100, 30));

                // Action listener for remove button
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BookmarkManager.removeBookmark(artist.getName());
                        updateBookmarks();
                        JOptionPane.showMessageDialog(BookmarkWindow.this, artist.getName() + " removed from bookmarks.");
                    }
                });

                bottomPanel.add(removeButton);
                artistCardPanel.add(bottomPanel, BorderLayout.SOUTH);

                // Add artist card to the main panel with spacing
                panel.add(Box.createVerticalStrut(10));  // Space between artist cards
                panel.add(artistCardPanel);
            }
        }

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }
}
