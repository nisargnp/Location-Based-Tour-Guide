package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tour implements Serializable {

    private String id;
    private String name;
    private double lat;
    private double lon;
    private String description;
    private String author;
    private String pictureFile;
    private int rating;
    private int numVotes;
    private List<String> places;
    private List<String> comments;

    public Tour(){
        this("","",0,0,"","","",0,0,new ArrayList<>(),new ArrayList<>());
    }

    private Tour(String id, String name, double lat, double lon, String description, String author, String pictureFile, int rating, int numVotes, List<String> places, List<String> comments) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.description = description;
        this.author = author;
        this.pictureFile = pictureFile;
        this.rating = rating;
        this.numVotes = numVotes;
        this.places = places;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", pictureFile='" + pictureFile + '\'' +
                ", rating=" + rating +
                ", numVotes=" + numVotes +
                ", places=" + places +
                ", comments=" + comments +
                '}';
    }

}
