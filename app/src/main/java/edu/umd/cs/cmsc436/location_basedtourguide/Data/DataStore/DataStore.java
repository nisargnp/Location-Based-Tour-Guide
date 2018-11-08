package edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;

public class DataStore {

    private static DataStore dataStore = new DataStore() {};

    private Map<String, Comment> commentMap;
    private Map<String, Place> placeMap;
    private Map<String, Tour> tourMap;
    private Map<String, User> userMap;

    private DataStore() {
        commentMap = new LinkedHashMap<>();
        placeMap = new LinkedHashMap<>();
        tourMap = new LinkedHashMap<>();
        userMap = new LinkedHashMap<>();
    }

    public static DataStore getInstance() {
        return dataStore;
    }

    // setters
    public void addComment(Comment comment) {
        commentMap.put(comment.getId(), comment);
    }

    public void addPlace(Place place) {
        placeMap.put(place.getId(), place);
    }

    public void addTour(Tour tour) {
        tourMap.put(tour.getId(), tour);
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
    }

    // list setters
    public void addComments(List<Comment> comments) {
        for (Comment comment : comments)
            addComment(comment);
    }

    public void addPlaces(List<Place> places) {
        for (Place place : places)
            addPlace(place);
    }

    public void addTours(List<Tour> tours) {
        for (Tour tour : tours)
            addTour(tour);
    }

    public void addUsers(List<User> users) {
        for (User user : users)
            addUser(user);
    }

    // getters
    public Comment getComment(String id) {
        return commentMap.containsKey(id) ? commentMap.get(id) : null;
    }

    public Place getPlace(String id) {
        return placeMap.containsKey(id) ? placeMap.get(id) : null;
    }

    public Tour getTour(String id) {
        return tourMap.containsKey(id) ? tourMap.get(id) : null;
    }

    public User getUser(String id) {
        return userMap.containsKey(id) ? userMap.get(id) : null;
    }

    // list getters -- ids
    public List<String> getCommentIDs() {
        return new ArrayList<>(commentMap.keySet());
    }

    public List<String> getPlaceIDs() {
        return new ArrayList<>(placeMap.keySet());
    }

    public List<String> getTourIDs() {
        return new ArrayList<>(tourMap.keySet());
    }

    public List<String> getUserIDs() {
        return new ArrayList<>(userMap.keySet());
    }

    // list getters -- data objects
    public List<Comment> getComments() {
        return new ArrayList<>(commentMap.values());
    }

    public List<Place> getPlaces() {
        return new ArrayList<>(placeMap.values());
    }

    public List<Tour> getTours() {
        return new ArrayList<>(tourMap.values());
    }

    public List<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

}
