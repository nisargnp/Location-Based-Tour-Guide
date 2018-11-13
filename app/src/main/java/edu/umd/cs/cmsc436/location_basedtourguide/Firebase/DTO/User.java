package edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String name;
    private List<String> tours;

    public void updateUser(User u){
        setName(u.getName());
        setTours(u.getTours());
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
