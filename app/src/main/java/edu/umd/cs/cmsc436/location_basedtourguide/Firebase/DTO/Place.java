package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Place implements Serializable {

    private String id;
    private String name;
    private String description;
    private double lat;
    private double lon;
    private String pictureFile; // these are filenames @conor
    private String videoFile;
    private String audioFile;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebasePlaces = database.getReference("Places");//is this the right ref?
    private DatabaseReference thisPlace;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public Place(){}

    public Place(String name){
        this(null,name,null,0,0,null,null,null);
        id = firebasePlaces.push().getKey();
        thisPlace = firebasePlaces.child(id);
        setId(id);
        thisPlace.setValue(this);
    }

    public Place(String i, String n,String d,double la,double lo,String pic,String vid,String aud){
        this.id = i;
        this.name = n;
        this.description = d;
        this.lat = la;
        this.lon = lo;
        this.pictureFile = pic;
        this.videoFile = vid;
        this.audioFile = aud;
    }

    public void updatePlace(Place p){
        setId(p.getId());
        setName(p.getName());
        setLat(p.getLat());
        setLon(p.getLon());
        setDescription(p.getDescription());
        setPictureFile(p.getPictureFile());
        setVideoFile(p.getVideoFile());
        setAudioFile(p.getAudioFile());
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
        Map<String,Object> ups = new HashMap<>();
        ups.put("name",name);
        thisPlace.updateChildren(ups);
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("lat",lat);
        thisPlace.updateChildren(ups);
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("lon",lon);
        thisPlace.updateChildren(ups);
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("description",description);
        thisPlace.updateChildren(ups);
        this.description = description;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("pictureFile",pictureFile);
        thisPlace.updateChildren(ups);
        this.pictureFile = pictureFile;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("videoFile",videoFile);
        thisPlace.updateChildren(ups);
        this.videoFile = videoFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("audioFile",audioFile);
        thisPlace.updateChildren(ups);
        this.audioFile = audioFile;
    }

}
