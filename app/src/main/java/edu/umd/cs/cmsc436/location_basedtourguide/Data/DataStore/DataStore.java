package edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DataStore {

    private static DataStore dataStore = new DataStore() {};

    private Map<String, Comment> commentMap;
    private Map<String, Place> placeMap;
    private Map<String, Tour> tourMap;
    private Map<String, User> userMap;

    //used for back end work
    private  List<Comment> comments;
    private  List<Place> places;
    private  List<Tour> tours;
    private  List<User> users;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference firebaseRefTours = database.getReference("Tours");
    private static DatabaseReference firebaseRefPlaces = database.getReference("Places");
    private static DatabaseReference firebaseRefComments = database.getReference("Comments");
    private static DatabaseReference firebaseRefUsers = database.getReference("Users");


    private DataStore() {
        commentMap = new LinkedHashMap<>();
        placeMap = new LinkedHashMap<>();
        tourMap = new LinkedHashMap<>();
        userMap = new LinkedHashMap<>();

        ValueEventListener TourListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                update();
                Tour ChangedTour = dataSnapshot.getValue(Tour.class);
                String i = ChangedTour.getId();
                Tour ThisTour = null;
                for(Tour t : tours){
                    String id = t.getId();
                    if(i.equals(id)){
                        t.updateTour(ChangedTour);
                    }
                }
                if(ThisTour == null){
                    tours.add(ChangedTour);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.i("","Canceled Read");
            }
        };

        ValueEventListener PlaceListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                update();
                Place ChangedPlace = dataSnapshot.getValue(Place.class);
                String i = ChangedPlace.getId();
                Place ThisPlace = null;
                for(Place p : places){
                    String id = p.getId();
                    if(i.equals(id)){
                        p.updatePlace(ChangedPlace);
                    }
                }
                if(ThisPlace == null){
                    places.add(ChangedPlace);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.i("","Canceled Read");
            }
        };

        ValueEventListener CommentListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                update();
                Comment ChangedComment = dataSnapshot.getValue(Comment.class);
                String i = ChangedComment.getId();
                Comment ThisComment = null;
                for(Comment p : comments){
                    String id = p.getId();
                    if(i.equals(id)){
                        p.updateComment(ChangedComment);
                    }
                }
                if(ThisComment == null){
                    comments.add(ChangedComment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.i("","Canceled Read");
            }
        };

        ValueEventListener UserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                update();
                User ChangedUser = dataSnapshot.getValue(User.class);
                String i = ChangedUser.getId();
                User ThisUser = null;
                for(User p : users){
                    String id = p.getId();
                    if(i.equals(id)){
                        p.updateUser(ChangedUser);
                    }
                }
                if(ThisUser == null){
                    users.add(ChangedUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.i("","Canceled Read");
            }
        };

        if(firebaseRefTours != null) {
            firebaseRefTours.addValueEventListener(TourListener);
        }
        if(firebaseRefPlaces != null) {
            firebaseRefPlaces.addValueEventListener(PlaceListener);
        }
        if(firebaseRefComments != null) {
            firebaseRefComments.addValueEventListener(CommentListener);
        }
        if(firebaseRefUsers != null) {
            firebaseRefUsers.addValueEventListener(UserListener);
        }

    }

    public void update(){
        tours = new ArrayList<Tour>(tourMap.values());
        places = new ArrayList<Place>(placeMap.values());
        comments = new ArrayList<Comment>(commentMap.values());
        users = new ArrayList<User>(userMap.values());
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
