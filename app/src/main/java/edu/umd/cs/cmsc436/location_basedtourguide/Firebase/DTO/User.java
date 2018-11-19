package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String name;
    private List<String> tours;

    public User(){
        this("","",new ArrayList<>());
    }

    private User(String id, String name, List<String> tours) {
        this.id = id;
        this.name = name;
        this.tours = tours;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tours=" + tours +
                '}';
    }

}
