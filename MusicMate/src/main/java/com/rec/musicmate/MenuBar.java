package com.rec.musicmate;

import javax.swing.*;
import java.awt.*;

public class MenuBar {

    public static JMenuBar createMenuBar(JFrame parent) {
        JMenuBar menuBar = new JMenuBar();

        // Home menu
        JMenu homeMenu = new JMenu("Home");
        homeMenu.setFont(new Font("Ubuntu", Font.BOLD, 18));

        JMenuItem home = new JMenuItem("Home");
        home.setFont(new Font("Ubuntu", Font.BOLD, 18));
        home.addActionListener(e -> {
            // Handle Home action (for example, open a new window or dispose the current one)
            new Home().setVisible(true); // Open the Home window
            parent.dispose();  // Close the current window
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(new Font("Ubuntu", Font.BOLD, 18));
        exit.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                parent,  // Use 'parent' instead of 'this'
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);  // Close the application if "Yes"
            }
        });
        
        homeMenu.add(home);
        homeMenu.add(exit); // Added exit menu item under "Home"
        menuBar.add(homeMenu);

        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(new Font("Ubuntu", Font.BOLD, 18));

        // Add Artist item
        JMenuItem addItem = new JMenuItem("Add Artist");
        addItem.setFont(new Font("Ubuntu", Font.BOLD, 18));
        addItem.addActionListener(e -> {
            // Handle Add Artist action
            new AddArtistGUI().setVisible(true);;
        });
        editMenu.add(addItem);
     
        // Delete Artist item
        JMenuItem deleteItem = new JMenuItem("Delete Artist");
        deleteItem.setFont(new Font("Ubuntu", Font.BOLD, 18));
        deleteItem.addActionListener(e -> {
            // Handle Delete Artist action
          new RemoveArtistWindow().setVisible(true);
        });
        editMenu.add(deleteItem);

        // Bookmark item
        JMenuItem bookmarkItem = new JMenuItem("Bookmark");
        bookmarkItem.setFont(new Font("Ubuntu", Font.BOLD, 18));
        bookmarkItem.addActionListener(e -> {
            // Handle Bookmark action
         new BookmarkWindow().setVisible(true);
        });
        editMenu.add(bookmarkItem);

        menuBar.add(editMenu);
        JMenu searchmenu = new JMenu("Search");
        searchmenu.setFont(new Font("Ubuntu", Font.BOLD, 18));
        JMenuItem searchAritst = new JMenuItem("Search Artist");
        searchAritst.setFont(new Font("Ubuntu",Font.BOLD,18));

        searchAritst.addActionListener(e->{
            new SearchWindow().setVisible(true);
        });
        searchmenu.add(searchAritst);
        menuBar.add(searchmenu);

        // About menu
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setFont(new Font("Ubuntu", Font.BOLD, 18));
        JMenuItem aboutApp = new JMenuItem("About MusicMate");
        aboutApp.setFont(new Font("Ubuntu", Font.BOLD, 18));
        aboutApp.addActionListener(e -> {
            // Show information about the application in a dialog box
            JOptionPane.showMessageDialog(parent, "MusicMate v1.0\nA simple music management app.");
        });
        aboutMenu.add(aboutApp);
        menuBar.add(aboutMenu);

        return menuBar;
    }
}
