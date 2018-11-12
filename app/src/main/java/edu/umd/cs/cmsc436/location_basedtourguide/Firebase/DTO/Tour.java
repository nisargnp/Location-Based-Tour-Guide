package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseTours = database.getReference("project/location-based-tour-guid-31237/database/firestore/data~2F").child("Tours");//is this the right ref?
    private DatabaseReference thisTour;

    //call this when you want a new tour built
    public Tour() {
        id = firebaseTours.push().getKey();
        Tour t = new Tour(id,null,0,0,null,null,null,0,0,null,null);
        firebaseTours.setValue(t);
        thisTour = firebaseTours.child(id);
    }
    //shouldnt call this one?
    public Tour(String i, String n, double la, double lo, String des, String au,String pic,int rat, int nu, List<String> pl, List<String> c){
        id = i;
        name = n;
        lat = la;
        lon = lo;
        description = des;
        author = au;
        pictureFile = pic;
        rating = rat;
        numVotes = nu;
        places = pl;
        comments = c;
    }

    public void updateTour(Tour t){

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

    public void setId(String id) {//this shouldnt be called
        Map<String, Object> ups = new HashMap<>();
        ups.put(this.id,id);
        firebaseTours.updateChildren(ups);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("name",name);
        thisTour.updateChildren(ups);
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("lat",lat);
        thisTour.updateChildren(ups);
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("lon",lon);
        thisTour.updateChildren(ups);
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("description",description);
        thisTour.updateChildren(ups);
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("author",author);
        thisTour.updateChildren(ups);
        this.author = author;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("pictureFile",pictureFile);
        thisTour.updateChildren(ups);
        this.pictureFile = pictureFile;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("rating",rating);
        thisTour.updateChildren(ups);
        this.rating = rating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("numVotes",numVotes);
        thisTour.updateChildren(ups);
        this.numVotes = numVotes;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("places",places);
        thisTour.updateChildren(ups);
        this.places = places;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("comments",comments);
        thisTour.updateChildren(ups);
        this.comments = comments;
    }

}
