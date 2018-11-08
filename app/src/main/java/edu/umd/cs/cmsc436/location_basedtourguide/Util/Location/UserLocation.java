package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;

/**
 * Very small helper class to distinguish user location from tour stop when drawing a tour route
 */
public class UserLocation extends Place {
    public UserLocation(double lat, double lng) {
        super();
        this.setName("Dummy User Location Class");
        this.setLat(lat);
        this.setLon(lng);
    }
}
