package edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Parameters for the DirectionsAsyncTask. Subset of parameters found in the docs:
 * https://developers.google.com/maps/documentation/directions/intro#RequestParameters
 */
public class DirectionsTaskParameter {
    private static final String baseURI = "https://maps.googleapis.com/maps/api/directions/json";
    private static final String directionsKey = "***REMOVED***";

    private GoogleMap map;
    /**
     * Strings representing the Lat/Lng pair of a location. Must be separated by a single comma,
     * no spaces. Ex. "-37.143,55.544"
     */
    private String origin;
    private String destination;
    private String mode;
    // TODO - support waypoints

    public DirectionsTaskParameter(GoogleMap map, String origin, String destination, String mode) {
        this.map = map;
        this.origin = origin;
        this.destination = destination;
        this.mode = mode;
    }

    public DirectionsTaskParameter(GoogleMap map, LatLng originCoords, LatLng destinationCoords, String mode) {
        this.map = map;
        // manually toString to remove spaces
        this.origin = originCoords.latitude + "," + originCoords.longitude;
        this.destination = destinationCoords.latitude + "," + destinationCoords.longitude;
        this.mode = mode;
    }

    public String buildURI() {
        return baseURI + "?key=" + directionsKey + "&origin=" + origin + "&destination="
                + destination + "&mode=" + mode;
    }

    public GoogleMap getMap() {
        return map;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getMode() {
        return mode;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Map: " + map == null ? "null" : map.toString() + ", "
                + "Origin: " + origin + ", "
                + "Destination: " + destination + ", "
                + "Mode: " + mode;
    }
}
