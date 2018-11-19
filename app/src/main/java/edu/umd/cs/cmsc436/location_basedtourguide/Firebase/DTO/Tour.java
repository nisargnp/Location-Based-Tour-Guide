package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;


public class Tour implements Serializable {

    private String id;
    private String name;
    private double lat;
    private double lon;
    private String description;
    private String author;
    private String pictureFile; // this is a filename @conor
    private int rating;
    private int numVotes;
    private List<String> places;
    private List<String> comments;

    private DatabaseReference thisTour;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public Tour(){}

    public static Tour createTour() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference firebaseTours = database.getReference("Tours");

        Tour tour = new Tour("","",0,0,"","","",0,0,new ArrayList<>(),new ArrayList<>());
        tour.id = firebaseTours.push().getKey();
        tour.thisTour = firebaseTours.child(tour.id);
        tour.setId(tour.id);
        tour.thisTour.setValue(tour);
        return tour;
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

    public void updateTour(Tour t){
        setId(t.getId());
        setName(t.getName());
        setLat(t.getLat());
        setLon(t.getLon());
        setDescription(t.getDescription());
        setAuthor(t.getAuthor());
        setPictureFile(t.getPictureFile());
        setRating(t.getRating());
        setNumVotes(t.getNumVotes());
        setPlaces(t.getPlaces());
        setComments(t.getComments());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        thisTour.updateChildren(Utils.generatePair("id", id));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        thisTour.updateChildren(Utils.generatePair("name", name));
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
        thisTour.updateChildren(Utils.generatePair("lat", lat));
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
        thisTour.updateChildren(Utils.generatePair("lon", lon));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        thisTour.updateChildren(Utils.generatePair("description",description));
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        thisTour.updateChildren(Utils.generatePair("author", author));
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
        thisTour.updateChildren(Utils.generatePair("pictureFile", pictureFile));
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        thisTour.updateChildren(Utils.generatePair("rating",rating));
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
        thisTour.updateChildren(Utils.generatePair("numVotes", numVotes));
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
        thisTour.updateChildren(Utils.generatePair("places",places));
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
        thisTour.updateChildren(Utils.generatePair("comments", comments));
    }

}
