package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "tour-activity";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // Add a map to the MapView
//        MapFragment mMapFragment = MapFragment.newInstance();
//        mMapFragment.getMapAsync(this);
//
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.tourMapView, mMapFragment);
//        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicked button");
                DownloadTask task = new DownloadTask();
                task.execute("test p1", "test p2");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapEventListeners();

        // TODO - change this to zoom to user location (check for permissions)
        // TODO - add markers based on passed in tour
        LatLng collegePark = new LatLng(38.9897, -76.9378);
        mMap.addMarker(new MarkerOptions()
                .position(collegePark)
                .title("Marker in College Park")
                .snippet("Some description text")
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(collegePark));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void setMapEventListeners() {
        // TODO - on info window click, send to appropriate place detail activity
        // TODO - do anything besides default zoom and show info window on marker click?
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i(TAG, marker.getTitle() + "'s Info Window Clicked");
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, List<String>> {
        private static final String URI = "https://api.umd.io/v0";
//                "https://maps.googleapis.com/maps/api/directions/json?key=***REMOVED***&origin=38.980367,-76.942366&destination=38.990085,-76.936182";

        protected List<String> doInBackground(String... params) {
            String data = null;
            HttpURLConnection httpURLConnection = null;

            try {
                httpURLConnection = (HttpURLConnection) new URL(URI).openConnection();

                InputStream in = new BufferedInputStream(
                        httpURLConnection.getInputStream());

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


            Log.i(TAG, "BG with " + params.length);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return parseJsonString(data);
        }

        protected void onPostExecute(List<String> result) {
            Log.i(TAG, "post exec with res:\n" + result);
            ((TextView) findViewById(R.id.testTextView)).setText(result.get(0));
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, "Reading from JSON source");
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

        private List<String> parseJsonString(String data) {

            String ID_TAG = "id";
            String VERSION_TAG = "version";
            String NAME_TAG = "name";

            List<String> result = new ArrayList<>();

            try {
                // Get top-level JSON Object - a Map
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        data).nextValue();

                result.add(ID_TAG + ":"
                        + responseObject.getString(ID_TAG) + ","
                        + VERSION_TAG + ":"
                        + responseObject.getString(VERSION_TAG) + ","
                        + NAME_TAG + ":"
                        + responseObject.get(NAME_TAG));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
}