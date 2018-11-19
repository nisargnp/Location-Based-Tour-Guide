package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;

public class Place implements Serializable {

    private String id;
    private String name;
    private String description;
    private double lat;
    private double lon;
    private String pictureFile;
    private String videoFile;
    private String audioFile;

    public Place(){
        this("","","",0,0,"","","");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", pictureFile='" + pictureFile + '\'' +
                ", videoFile='" + videoFile + '\'' +
                ", audioFile='" + audioFile + '\'' +
                '}';
    }

    /*
    Methods for local, non-Firebase tracked, place objects
     */
    public void setLocalName(String name) { this.name = name; }

    public void setLocalLat(double lat) { this.lat = lat; }

    public void setLocalLon(double lon) { this.lon = lon; }
}
