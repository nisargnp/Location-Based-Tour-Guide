package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsAsyncTask;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsTaskParameter;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "tour-activity";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // Add a map to the MapView
        MapFragment mMapFragment = MapFragment.newInstance();
        mMapFragment.getMapAsync(this);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.tourMapView, mMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // temporary for testing directions API
        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked Directions API test button");

                if (mMap != null) {
                    LatLng terrapinRow = new LatLng(38.980367, -76.942366);
                    LatLng CSIC = new LatLng(38.990085, -76.936182);

                    // Add markers
                    mMap.addMarker((new MarkerOptions())
                            .position(terrapinRow)
                            .title("Marker at Terrapin Row")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.addMarker((new MarkerOptions())
                            .position(CSIC)
                            .title("Marker at Computer Science Instruction Complex?")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                    // Query Directions API in background. AsyncTask will draw route lines for us.
                    DirectionsAsyncTask task = new DirectionsAsyncTask();
                    DirectionsTaskParameter param = new DirectionsTaskParameter(mMap,
                            terrapinRow,
                            CSIC,
                            "walking");
                    task.execute(param);
                }
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
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
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
}