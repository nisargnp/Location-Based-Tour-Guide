package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class User implements Serializable {

    private String id;
    private String name;
    private List<String> tours;

    private DatabaseReference thisUser;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public User(){}

    public static User createUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference firebaseUsers = database.getReference("Users");

        User user = new User("","",new ArrayList<>());
        user.id = firebaseUsers.push().getKey();
        user.thisUser = firebaseUsers.child(user.id);
        user.setId(user.id);
        user.thisUser.setValue(user);
        return user;
    }

    private User(String id, String name, List<String> tours) {
        this.id = id;
        this.name = name;
        this.tours = tours;
    }

    public void updateUser(User u){
        setId(u.getId());
        setName(u.getName());
        setTours(u.getTours());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        thisUser.updateChildren(Utils.generatePair("id", id));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        thisUser.updateChildren(Utils.generatePair("name", name));
    }

    public List<String> getTours() {
        return tours;
    }

    public void setTours(List<String> tours) {
        this.tours = tours;
        thisUser.updateChildren(Utils.generatePair("tours", tours));
    }

}
