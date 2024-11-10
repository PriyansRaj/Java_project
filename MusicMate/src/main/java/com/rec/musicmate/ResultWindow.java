package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ResultWindow extends JFrame {
    public ResultWindow(List<Artist> artists, String searchTerm) {
        setTitle("Search Results for: " + searchTerm);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with gradient background
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title Label for search results
        JLabel resultTitleLabel = new JLabel("Search Results for: " + searchTerm);
        resultTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        resultTitleLabel.setForeground(new Color(255, 87, 34)); // Matching color
        resultTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        resultTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0)); // Add padding around the title

        mainPanel.add(resultTitleLabel);

        // Scroll pane to hold artist panels
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        // Iterate through artists and create artist panels
        for (Artist artist : artists) {
            mainPanel.add(createArtistPanel(artist));
        }

        setVisible(true);
    }
    private JPanel createArtistPanel(Artist artist) {
        JPanel artistPanel = new JPanel(new BorderLayout());
        artistPanel.setBackground(new Color(50, 50, 50));
        artistPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        artistPanel.setPreferredSize(new Dimension(800, 140));
        artistPanel.setMaximumSize(new Dimension(800, 140));
    
        // Info panel to display artist details
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(50, 50, 50));
    
        JLabel nameLabel = new JLabel("Name: " + artist.getName());
        styleLabel(nameLabel, Color.WHITE, Font.BOLD, 18);
    
        JLabel genreLabel = new JLabel("Genre: " + String.join(", ", artist.getTags()));
        styleLabel(genreLabel, Color.LIGHT_GRAY, Font.PLAIN, 16);
    
        JLabel listenerLabel = new JLabel("Listeners: " + artist.getListeners());
        styleLabel(listenerLabel, Color.LIGHT_GRAY, Font.PLAIN, 16);
    
        JLabel relatedLabel = new JLabel("Related Artists: " + String.join(", ", artist.getRelatedArtists()));
        styleLabel(relatedLabel, Color.LIGHT_GRAY, Font.PLAIN, 16);
    
        infoPanel.add(nameLabel);
        infoPanel.add(genreLabel);
        infoPanel.add(listenerLabel);
        infoPanel.add(relatedLabel);
    
        artistPanel.add(infoPanel, BorderLayout.CENTER);
    
        // Create options label (three dots) for additional actions
        JLabel optionsLabel = new JLabel("⋮");
        styleLabel(optionsLabel, Color.WHITE, Font.BOLD, 24);
        optionsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
        // Popup menu with bookmark and bio options
        JPopupMenu optionsMenu = new JPopupMenu();
        JMenuItem addBookmarkItem = new JMenuItem("Add to Bookmark");
        JMenuItem removeBookmarkItem = new JMenuItem("Remove from Bookmark");
        JMenuItem bioItem = new JMenuItem("Bio");
    
        optionsMenu.add(addBookmarkItem);
        optionsMenu.add(removeBookmarkItem);
        optionsMenu.add(bioItem);
    
        // Show or hide add/remove options based on bookmark status
        updateBookmarkMenu(artist, addBookmarkItem, removeBookmarkItem);
    
        optionsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                optionsMenu.show(optionsLabel, e.getX(), e.getY());
            }
        });
    
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        optionsPanel.setBackground(new Color(50, 50, 50));
        optionsPanel.add(optionsLabel);
        artistPanel.add(optionsPanel, BorderLayout.NORTH);
    
        // Add action listeners for bookmark actions and bio
        addBookmarkItem.addActionListener(e -> {
            addBookmark(artist);
            updateBookmarkMenu(artist, addBookmarkItem, removeBookmarkItem);
        });
    
        removeBookmarkItem.addActionListener(e -> {
            removeBookmark(artist);
            updateBookmarkMenu(artist, addBookmarkItem, removeBookmarkItem);
        });
    
        bioItem.addActionListener(e -> showBio(artist));
    
        return artistPanel;
    }
    
    private void updateBookmarkMenu(Artist artist, JMenuItem addBookmarkItem, JMenuItem removeBookmarkItem) {
        boolean isBookmarked = BookmarkManager.getBookmarkedArtists().contains(artist);
        addBookmarkItem.setVisible(!isBookmarked);
        removeBookmarkItem.setVisible(isBookmarked);
    }
    
    private void addBookmark(Artist artist) {
        BookmarkManager.addBookmark(artist);
        JOptionPane.showMessageDialog(this, 
            artist.getName() + " added to bookmarks.\nGenre: " + 
            String.join(", ", artist.getTags()) + "\nListeners: " + artist.getListeners());
    }
    
    private void removeBookmark(Artist artist) {
        BookmarkManager.removeBookmark(artist.getName());
        JOptionPane.showMessageDialog(this, artist.getName() + " removed from bookmarks.");
    }
    
    
    private void styleLabel(JLabel label, Color color, int style, int size) {
        label.setForeground(color);
        label.setFont(new Font("Arial", style, size));
    }


    private void showBio(Artist artist) {
        JFrame bioFrame = new JFrame("Bio of " + artist.getName());
        bioFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        bioFrame.setLocationRelativeTo(null);
        
        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setText(artist.getBio());
        bioTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        bioTextArea.setEditable(false);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setLineWrap(true);
        bioTextArea.setBackground(new Color(30, 30, 30));
        bioTextArea.setForeground(Color.WHITE);

        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);
        bioScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bioScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        bioFrame.add(bioScrollPane);
        bioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bioFrame.setVisible(true);
    }

    // GradientPanel class for background
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
}
