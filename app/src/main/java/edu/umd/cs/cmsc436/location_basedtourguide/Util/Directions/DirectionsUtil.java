package edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;

public class DirectionsUtil {
    public static final String DEFAULT_TRANSPORTATION_MODE = "walking";

    public static float PLACE_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;

    public static void drawTourRoute(GoogleMap map, List<Place> tourStops, boolean drawMarkers) {
        if (tourStops.size() > 1) {
            if (drawMarkers) {
                addMarkers(map, tourStops);
            }

            // Query Directions API in background. The AsyncTask will draw route lines for us.
            DirectionsAsyncTask task = new DirectionsAsyncTask();
            DirectionsTaskParameter param = buildDirectionParams(map, tourStops);
            task.execute(param);
        }
    }

    private static DirectionsTaskParameter buildDirectionParams(GoogleMap map, List<Place> tourStops) {
        // assumes the invariant that tourStops has at least 2 tours
        Place startLocation = tourStops.get(0);
        Place endLocation = tourStops.get(tourStops.size() - 1);

        List<String> waypoints = new ArrayList<>();
        // Add all non-start/end tour stops to the list of intermediary waypoints
        for (int i = 1; i < tourStops.size() - 1; i++) {
            Place stop = tourStops.get(i);
            // don't have any spaces for LatLng string URL parameter
            waypoints.add(stop.getLat() + "," + stop.getLon());
        }

        DirectionsTaskParameter param = new DirectionsTaskParameter(map,
                new LatLng(startLocation.getLat(), startLocation.getLon()),
                new LatLng(endLocation.getLat(), endLocation.getLon()),
                DEFAULT_TRANSPORTATION_MODE);

        if (!waypoints.isEmpty()) {
            param.setWaypoints(waypoints);
        }

        return param;
    }

    private static void addMarkers(GoogleMap map, List<Place> tourStops) {
        for (Place place : tourStops) {
            map.addMarker((new MarkerOptions())
                    .position(new LatLng(place.getLat(), place.getLon()))
                    .title(place.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(PLACE_MARKER_COLOR)));
        }
    }
}
