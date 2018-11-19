package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {

    private String id;
    private String name;
    private List<String> tours;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseUsers = database.getReference("Users");
    private DatabaseReference thisUser;

    /**
     * Don't use this constructor, this is for Firebase only.
     */
    public User(){}

    public User(String name){
        this(null,name,null);
        id = firebaseUsers.push().getKey();
        thisUser = firebaseUsers.child(id);
        setId(id);
        thisUser.setValue(this);
    }

    public User(String id, String Name, List<String> Tours){
        this.id = id;
        this.name= Name;
        this.tours = Tours;
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
        Map<String, Object> ups = new HashMap<>();
        ups.put(this.id,id);
        firebaseUsers.updateChildren(ups);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Map<String, Object> ups = new HashMap<>();
        ups.put("name",name);
        firebaseUsers.updateChildren(ups);
        this.name = name;
    }

    public List<String> getTours() {
        return tours;
    }

    public void setTours(List<String> tours) {
        Map<String, Object> ups = new HashMap<>();
        ups.put("tours",tours);
        firebaseUsers.updateChildren(ups);
        this.tours = tours;
    }

}
