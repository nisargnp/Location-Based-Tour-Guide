package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class Place implements Serializable {

    private String id;
    private String name;
    private String description;
    private double lat;
    private double lon;
    private String pictureFile; // these are filenames @conor
    private String videoFile;
    private String audioFile;

    private DatabaseReference thisPlace;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public Place(){}

    public static Place createPlace() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference firebasePlaces = database.getReference("Places");

        Place place = new Place("","","",0,0,"","","");
        place.id = firebasePlaces.push().getKey();
        place.thisPlace = firebasePlaces.child(place.id);
        place.setId(place.id);
        place.thisPlace.setValue(place);
        return place;
    }

    private Place(String id, String name, String description, double lat, double lon, String pictureFile, String videoFile, String audioFile) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.pictureFile = pictureFile;
        this.videoFile = videoFile;
        this.audioFile = audioFile;
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
        thisPlace.updateChildren(Utils.generatePair("id", id));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        thisPlace.updateChildren(Utils.generatePair("name", name));
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
        thisPlace.updateChildren(Utils.generatePair("lat", lat));
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
        thisPlace.updateChildren(Utils.generatePair("lon", lon));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        thisPlace.updateChildren(Utils.generatePair("description", description));
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        Map<String,Object> ups = new HashMap<>();
        ups.put("pictureFile",pictureFile);
        thisPlace.updateChildren(ups);
        this.pictureFile = pictureFile;
        thisPlace.updateChildren(Utils.generatePair("pictureFile", pictureFile));
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
        thisPlace.updateChildren(Utils.generatePair("videoFile", videoFile));
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
        thisPlace.updateChildren(Utils.generatePair("audioFile", audioFile));
    }

}
