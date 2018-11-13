package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Location.UserLocation;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "tour-activity";
    private static final int LOCATION_SERVICES_REQUEST_CODE = 1;
    private static final int GOOGLE_MAPS_LOCATION_REQUEST_CODE = 2;

    private FusedLocationProviderClient mFusedLocationClient;
    private TextView mTestText;
    private GoogleMap mMap;
    private Tour mTour;
    private int mNextStopIndex = -1;
    private List<Place> mTourPlaces;
    /**
     * Location objects to use Location helper functions for getting distance in meters
     */
    private List<Location> mTourLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // TODO - remove this test view
        mTestText = findViewById(R.id.testTextView);

        // for drawing route to user location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Add a map to the MapView
        MapFragment mMapFragment = MapFragment.newInstance();
        mMapFragment.getMapAsync(this);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.tourMapView, mMapFragment);
        fragmentTransaction.commit();

        Intent givenIntent = getIntent();
        if (givenIntent != null) {
            String tourID = givenIntent.getExtras().getString(MainActivity.TOUR_TAG);
            mTour = DataStore.getInstance().getTour(tourID);
            setTitle(mTour.getName());
        }

        if (mTour != null) {
            Log.d("TourActivity", "Tour Name: " + mTour.getName());

            // Get list of stops for the tour
            mTourPlaces = new ArrayList<>();
            mTourLocations = new ArrayList<>();
            for (String stopId : mTour.getPlaces()) {
                Place place = DataStore.getInstance().getPlace(stopId);
                // TODO - move this logic to DataStore
                mTourPlaces.add(place);

                Location location = new Location("temp");
                location.setLatitude(place.getLat());
                location.setLongitude(place.getLon());
                mTourLocations.add(location);

                // Initialize next tour stop
                mNextStopIndex = 0;
                mTestText.setText(mTourPlaces.get(mNextStopIndex).getName());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // temporary for testing directions API
        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked Directions API test button");
                mMap.clear();
                showTourRoute();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapEventListeners();

        LatLng tourCoordinates = new LatLng(mTour.getLat(), mTour.getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tourCoordinates));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        showUserLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == GOOGLE_MAPS_LOCATION_REQUEST_CODE || requestCode == LOCATION_SERVICES_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == LOCATION_SERVICES_REQUEST_CODE) {
                    renderTourRoute();
                } else {
                    enableLocationLayer();
                }
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
    private void showTourRoute() {
        if (needsRuntimePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_SERVICES_REQUEST_CODE);
        } else {
            renderTourRoute();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showUserLocation() {
        if (needsRuntimePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    GOOGLE_MAPS_LOCATION_REQUEST_CODE);
        } else {
            enableLocationLayer();
        }
    }

    private void enableLocationLayer() {
        if (mMap != null) {
            try {
                // show user location on map
                mMap.setMyLocationEnabled(true);

                // TODO - start tracking user location service
                // listen on message - broadcast receiver?
                // pass service next location lat lon somehow
                // increment index
                //

            } catch (SecurityException e) {
                // this catch is just here cause my IDE wasn't detecting the permission check
                Log.e(TAG, e.toString());
            }
        }
    }

    private void renderTourRoute() {
        if (mMap != null) {
            try {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        List<Place> tourStops = new ArrayList<>();
                        if (location != null) {
                            UserLocation userLocation = new UserLocation(location.getLatitude(),
                                    location.getLongitude());
                            tourStops.add(userLocation);
                        }
                        tourStops.addAll(mTourPlaces);
                        DirectionsUtil.drawTourRoute(mMap, tourStops, true);
                    }
                });
            } catch (SecurityException e) {
                // this catch is just here cause my IDE wasn't detecting the permission check
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * Call when the user reaches the current tour stop. Will update UI and variables for the next
     * tour stop
     */
    private void nextTourStop() {
        // show notification for stop just arrived at, if not beginning of tour
        Place arrivedPlace = mTourPlaces.get(mNextStopIndex);
        String notificationMessage = "Arrived at " + arrivedPlace.getName();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.snackBarView), notificationMessage, 10000);
        snackbar.setAction("More Details", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - actually play media
                Log.i(TAG, "Play media for: " + arrivedPlace.getName());
            }
        });
        snackbar.show();

        // increment tour stop
        mNextStopIndex += 1;
        // TODO - actually update the place preview view when it is made
        mTestText.setText(mTourPlaces.get(mNextStopIndex).getName());
    }

    private boolean needsRuntimePermission(String permission) {
        // Check the SDK version and whether the permission is already granted.
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }
}
