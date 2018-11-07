package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DataProvider.DataProvider;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "tour-activity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
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

        // TODO: use tour object to display correct data
//        Tour tour = (Tour) getIntent().getExtras().get(MainActivity.TOUR_TAG);
//        if (tour != null) Toast.makeText(TourActivity.this, "Tour Name: " + tour.getName(), Toast.LENGTH_LONG).show();
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
                    // TODO - get list of tour places and delete this test button
                    List<Place> tourStops = DataProvider.getPlaces();
                    DirectionsUtil.drawTourRoute(mMap, tourStops, true);
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

        showUserLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                enableLocationLayer();
            } else {
                Toast.makeText(this, "This app requires access to your location data!",
                        Toast.LENGTH_SHORT).show();
            }
        }
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

    @TargetApi(Build.VERSION_CODES.M)
    private void showUserLocation() {
        if (needsRuntimePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableLocationLayer();
        }
    }

    private void enableLocationLayer() {
        if (mMap != null) {
            try {
                mMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                // this catch is just here cause my IDE wasn't detecting the permission check
                Log.e(TAG, e.toString());
            }
        }
    }

    private boolean needsRuntimePermission(String permission) {
        // Check the SDK version and whether the permission is already granted.
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }
}
