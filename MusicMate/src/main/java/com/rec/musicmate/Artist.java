package com.rec.musicmate;

import java.util.List;

public class Artist {
    private String name;
    private String bio;
    private String imageUrl;
    private String[] tags;
    private int listeners;
    private int playcount;
    private List<String> relatedArtists; // Field for storing related artist names

    // Constructor to initialize an Artist object
    public Artist(String name, String bio, String imageUrl, String[] tags, int listeners, int playcount, List<String> relatedArtists) {
        this.name = name;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.listeners = listeners;
        this.playcount = playcount;
        this.relatedArtists = relatedArtists;
    }

    public Artist(String name2, String genre, String listeners2, String relatedArtists2, String bio2) {
        //TODO Auto-generated constructor stub
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public List<String> getRelatedArtists() {
        return relatedArtists;
    }

    public void setRelatedArtists(List<String> relatedArtists) {
        this.relatedArtists = relatedArtists;
    }

    // Optional: Override toString for better readability of Artist objects
    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + String.join(", ", tags) +
                ", listeners=" + listeners +
                ", playcount=" + playcount +
                ", relatedArtists=" + String.join(", ", relatedArtists) +
                '}';
    }
}
