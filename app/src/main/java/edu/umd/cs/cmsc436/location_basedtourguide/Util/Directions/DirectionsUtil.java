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

    /**
     * Draw a Tour's (walking) route on a GoogleMap
     * @param map - GoogleMap to draw on.
     * @param tourStops - List of Place objects representing tour stops. MUST be in order.
     * @param drawMarkers - Flag whether to draw markers along with the tour route.
     */
    public static void drawTourRoute(GoogleMap map, List<Place> tourStops, boolean drawMarkers) {
        if (tourStops.size() > 1) {
            if (drawMarkers) {
                addMarkers(map, tourStops);
            }

            // Query Directions API in background. The AsyncTask will draw route lines for us.
            DirectionsAsyncTask task = new DirectionsAsyncTask();
            DirectionsTaskParameter param = buildDirectionParams(map, tourStops);
            task.execute(param);

            // TODO - Zoom map after drawing... do in AsyncTask since we don't have access to JSON here?
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

    /**
     * Draw markers on a GoogleMap based on the locations in the given list of places
     * @param map
     * @param tourStops
     */
    private static void addMarkers(GoogleMap map, List<Place> tourStops) {
        for (Place place : tourStops) {
            // TODO - decide marker details
            map.addMarker((new MarkerOptions())
                    .position(new LatLng(place.getLat(), place.getLon()))
                    .title(place.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(PLACE_MARKER_COLOR)));
        }
    }
}
