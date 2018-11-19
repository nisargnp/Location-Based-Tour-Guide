package edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Location.UserLocation;

public class DirectionsUtil {
    public static final String DEFAULT_TRANSPORTATION_MODE = "walking";

    public static float PLACE_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;

    /**
     * Draw a Tour's (walking) route on a GoogleMap
     * @param map - GoogleMap to draw on.
     * @param tourStops - List of Place objects representing tour stops. MUST be in order.
     */
    public static void drawTourRoute(GoogleMap map, List<Place> tourStops) {
        if (tourStops.size() > 1) {
            // Query Directions API in background. The AsyncTask will draw route lines for us.
            DirectionsAsyncTask task = new DirectionsAsyncTask();
            DirectionsTaskParameter param = buildDirectionParams(map, tourStops);
            task.execute(param);
        }
    }

    /**
     * Draw markers on a GoogleMap based on the locations in the given list of places
     * @param map
     * @param tourStops
     */
    public static void drawTourMarkers(GoogleMap map, List<Place> tourStops) {
        if (tourStops.size() > 1) {
            // TODO - change color of next stop marker?
            // TODO - different color for visited stops?
            for (int i = 0; i < tourStops.size(); i++) {
                Place place = tourStops.get(i);
                if (!(place instanceof UserLocation)) {
                    Marker marker = map.addMarker((new MarkerOptions())
                            .position(new LatLng(place.getLat(), place.getLon()))
                            .title(place.getName())
                            .snippet(place.getDescription())
                            .icon(BitmapDescriptorFactory.defaultMarker(PLACE_MARKER_COLOR)));
                    marker.setTag(i);
                }
            }
        }
    }

    /**
     * Build DirectionsTaskParameter object for a DirectionsAPI query based on a passed in list of
     * tour stops.
     * @param map
     * @param tourStops
     * @return
     */
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
}
