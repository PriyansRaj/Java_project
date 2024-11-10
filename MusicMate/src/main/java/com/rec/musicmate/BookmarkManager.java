package com.rec.musicmate;

import java.util.ArrayList;
import java.util.List;

public class BookmarkManager {
    private static List<Artist> bookmarkedArtists = new ArrayList<>();

    // Add artist to bookmarks
    public static void addBookmark(Artist artist) {
        bookmarkedArtists.add(artist);
    }

    // Remove artist from bookmarks by name
    public static void removeBookmark(String artistName) {
        bookmarkedArtists.removeIf(artist -> artist.getName().equals(artistName));
    }

    // Get all bookmarked artists
    public static List<Artist> getBookmarkedArtists() {
        return new ArrayList<>(bookmarkedArtists);
    }

    // Get names of all bookmarked artists
    public static String[] getBookmarkedArtistNames() {
        return bookmarkedArtists.stream().map(Artist::getName).toArray(String[]::new);
    }
}
