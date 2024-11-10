package com.rec.musicmate;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import java.util.List;  // Correct import
import java.util.ArrayList;

public class MongoDBHelper {
    private static final String CONNECTION_URI = "mongodb://localhost:27017"; // Change this if needed
    private static final String DB_NAME = "music_data";  // Database name
    private static final String COLLECTION_NAME = "artists";  // Collection name

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBHelper() {
        try {
            // Connect to MongoDB server
            mongoClient = MongoClients.create(CONNECTION_URI);
            database = mongoClient.getDatabase(DB_NAME);
            collection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }
    public boolean insertArtist(Artist artist) {
        try {
            Document artistDoc = artistToDocument(artist);
            collection.insertOne(artistDoc);
            return true;
        } catch (Exception e) {
            System.err.println("Error inserting artist: " + e.getMessage());
            return false;
        }
    }
    // Fetch artist by name (case insensitive)
    public Artist getArtistByName(String name) {
        try {
            Bson filter = Filters.regex("name", "^" + name + "$", "i"); // Case-insensitive regex match
            Document artistDoc = collection.find(filter).first();
            return artistDoc != null ? documentToArtist(artistDoc) : null;
        } catch (Exception e) {
            System.err.println("Error fetching artist by name: " + e.getMessage());
            return null;
        }
    }

    // Fetch artists by genre (tags) - support partial match
    public ArrayList<Artist> getArtistsByGenre(String genre) {
        ArrayList<Artist> artists = new ArrayList<>();  // Using List<Artist> from java.util
        try {
            // Match genre partially (case insensitive)
            Bson filter = Filters.regex("tags", ".*" + genre + ".*", "i"); // Regex for partial matching
            FindIterable<Document> docs = collection.find(filter);

            for (Document doc : docs) {
                artists.add(documentToArtist(doc));
            }
        } catch (Exception e) {
            System.err.println("Error fetching artists by genre: " + e.getMessage());
        }
        return artists;
    }

    // Convert Document to Artist
    private Artist documentToArtist(Document doc) {
        String name = doc.getString("name");
        String bio = doc.getString("bio");
        String imageUrl = doc.getString("image");
        List<String> tags = doc.getList("tags", String.class);
        
        // Convert listeners and playcount to integers safely
        int listeners = parseInteger(doc.get("listeners"));
        int playcount = parseInteger(doc.get("playcount"));
    
        return new Artist(name, bio, imageUrl, tags.toArray(new String[0]), listeners, playcount, tags);
    }
    
    private int parseInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0; // Default value if the String cannot be parsed
            }
        }
        return 0; // Default value if the value is not a valid Number or String
    }
    private Document artistToDocument(Artist artist) {
        Document doc = new Document("name", artist.getName())
            .append("bio", artist.getBio())
            .append("image", artist.getImageUrl())
            .append("tags", List.of(artist.getTags()))
            .append("listeners", artist.getListeners())
            .append("playcount", artist.getPlaycount());
        return doc;
    }
    
    // Close MongoDB connection
    public void close() {
        try {
            if (mongoClient != null) {
                mongoClient.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing MongoDB connection: " + e.getMessage());
        }
    }
  
}