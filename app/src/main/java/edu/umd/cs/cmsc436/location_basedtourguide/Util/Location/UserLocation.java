package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;

public class UserLocation extends Place {
    public UserLocation(double lat, double lng) {
        super();
        this.setName("Dummy User Location Class");
        this.setLat(lat);
        this.setLon(lng);
    }
}
