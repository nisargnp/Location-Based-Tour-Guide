package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String name;
    private List<String> tours;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseUsers = database.getReference("Users");//.child("Tours");//is this the right ref?
    private DatabaseReference thisUser;

    public void updateUser(User u){
        setName(u.getName());
        setTours(u.getTours());
    }

    public User(){

    }
    public User(int n){
        this(null,null,null);
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

    public List<String> getTours() {
        return tours;
    }

    public void setTours(List<String> tours) {
        this.tours = tours;
    }

}
