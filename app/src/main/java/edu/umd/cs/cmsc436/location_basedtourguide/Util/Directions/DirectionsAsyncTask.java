package edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Async Task for querying Google Directions API. Takes in a DirectionTaskParameter, and draws
 * the route between each waypoint on a GoogleMap
 */
public class DirectionsAsyncTask extends AsyncTask<DirectionsTaskParameter, Void, List<String>> {
    private String TAG = "directions-async-task";
    private DirectionsTaskParameter directionsTaskParameter;
    private LatLngBounds routeBounds = null;

    protected List<String> doInBackground(DirectionsTaskParameter... params) {
        if (params.length < 1) {
            Log.w(TAG, "Directions Async Task received no parameters!");
            return null;
        }

        // expect only one argument, ignore all others
        directionsTaskParameter = params[0];
        String combinedURI = directionsTaskParameter.buildURI();

        Log.i(TAG, "Directions Async Task started with parameter: " + directionsTaskParameter.toString());

        String data = null;
        HttpURLConnection httpURLConnection = null;

        try {
            Log.i(TAG, "Directions URI: " + combinedURI);
            httpURLConnection = (HttpURLConnection) new URL(combinedURI).openConnection();

            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            data = readStream(in);

        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException");
        } catch (IOException exception) {
            Log.e(TAG, "IOException");
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return parseJsonString(data);
    }

    protected void onPostExecute(List<String> result) {
        Log.i(TAG, "Directions Task Result:\n" + result);

        // add a poly line to the map using Lat/Lng coordinates
        // assumes coordinates are in order from start location to end location (directions api default)
        if (directionsTaskParameter != null && directionsTaskParameter.getMap() != null) {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.MAGENTA);
            for (int i = 0; i < result.size(); i++) {
                String encodedPolyline = result.get(i);
                List<LatLng> stepPoints = PolyUtil.decode(encodedPolyline);
                polylineOptions.addAll(stepPoints);
            }

            GoogleMap map = directionsTaskParameter.getMap();
            // we may need to se the returned polyline to a static variable to delete a polyline
            // on redraw. Unless we can just clear the whole map somehow
            map.addPolyline(polylineOptions);
            if (routeBounds != null) {
                // TODO - adjust zoom padding?
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 150));
            }
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder data = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
//                Log.i(TAG, "Reading from JSON source");
                data.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException");
                }
            }
        }
        return data.toString();
    }

    /**
     * Parse JSON Response and return a list of the encoded polyline for each step
     * @param data
     * @return list of strings representing encoded polylines
     */
    private List<String> parseJsonString(String data) {
        String ROUTES_TAG = "routes";
        String LEGS_TAG = "legs";
        String STEPS_TAG = "steps";
        String POLYLINE_TAG = "polyline";
        String POINTS_TAG = "points";
        String BOUNDS_TAG = "bounds";
        String NE_TAG = "northeast";
        String SW_TAG = "southwest";
        String LAT_TAG = "lat";
        String LNG_TAG = "lng";

        List<String> result = new ArrayList<>();

        try {
            // check status for err?
            JSONObject responseObject = (JSONObject) new JSONTokener(data).nextValue();

            JSONArray routes = responseObject.getJSONArray(ROUTES_TAG);
            if (routes.length() > 0) {
                // if multiple routes, just use the first
                JSONObject routeObject = routes.getJSONObject(0);

                // get bounds for zooming
                JSONObject bounds = routeObject.getJSONObject(BOUNDS_TAG);
                JSONObject nwObj = bounds.getJSONObject(NE_TAG);
                JSONObject seObj = bounds.getJSONObject(SW_TAG);

                LatLng northeast = new LatLng(nwObj.getDouble(LAT_TAG),
                        nwObj.getDouble(LNG_TAG));
                LatLng southwest = new LatLng(seObj.getDouble(LAT_TAG),
                        seObj.getDouble(LNG_TAG));
                routeBounds = new LatLngBounds(southwest, northeast);

                // each leg is the route from one "tour stop" to the next
                JSONArray legs = routeObject.getJSONArray(LEGS_TAG);

                for (int legs_idx = 0; legs_idx < legs.length(); legs_idx++) {
                    JSONObject legObject = legs.getJSONObject(legs_idx);
                    JSONArray steps = legObject.getJSONArray(STEPS_TAG);

                    for (int steps_idx = 0; steps_idx < steps.length(); steps_idx++) {
                        JSONObject stepObject = steps.getJSONObject(steps_idx);
                        JSONObject polylineObject = stepObject.getJSONObject(POLYLINE_TAG);

                        result.add(polylineObject.getString(POINTS_TAG));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
