package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
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

import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.AudioDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.VideoDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo.PlaceInfoActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Location.LocationTrackingService;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Location.UserLocation;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TOUR_STOP_DATA = "tour-stop-data";
    public static final String PLACE_ID = "place-id";
    public static final String LOCATION_DATA_ACTION =
            "edu.umd.cs.cmsc436.location_basedtourguide.Tour.LOCATION_DATA_ACTION";
    public static final int IS_ALIVE = Activity.RESULT_FIRST_USER;

    private String TAG = "tour-activity";
    private static final int LOCATION_SERVICES_REQUEST_CODE = 1;
    private static final int GOOGLE_MAPS_LOCATION_REQUEST_CODE = 2;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private Tour mTour;
    private List<Place> mTourPlaces;
    private BroadcastReceiver mLocationDataReceiver;
    private int localNextStopIndex = 0;
    /**
     * Location objects to use Location helper functions for getting distance in meters
     */
    private List<Location> mTourLocations;
    private TextView mPreviewTitleView, mPreviewDescriptionView;
    private ImageView mPreviewImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // Next stop preview setup
        // TODO - do we need a title indicating that this preview is for the next stop?
        mPreviewImageView = findViewById(R.id.previewImage);
        mPreviewTitleView = findViewById(R.id.previewTitle);
        mPreviewDescriptionView = findViewById(R.id.previewDescription);
        findViewById(R.id.previewView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceInfo(LocationTrackingService.getNextStopIndex());
            }
        });

        // TODO - how do i get back to the main menu if i lose it on the backstack?
        // caused by single instance property. But need that for resuming tour.

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
        }

        if (mTour != null) {
            Log.d(TAG, "Tour Name: " + mTour.getName());

            setTitle(mTour.getName());
            // Get list of stops for the tour
            mTourPlaces = new ArrayList<>();
            mTourLocations = new ArrayList<>();
            for (String stopId : mTour.getPlaces()) {
                Place place = DataStore.getInstance().getPlace(stopId);
                mTourPlaces.add(place);

                // Working with location objects are easier for LocationAPI
                Location location = new Location("temp_img");
                location.setLatitude(place.getLat());
                location.setLongitude(place.getLon());
                mTourLocations.add(location);

                // Initialize next tour stop
                updatePreview(LocationTrackingService.getNextStopIndex());
            }
        }

        mLocationDataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "New LocationData intent received");
                if (isOrderedBroadcast()) {
                    refreshNextStop();
                    setResultCode(IS_ALIVE);
                } else {
                    Log.e(TAG, "NOT ORDERED BROADCAST, ABORTING!");
                    abortBroadcast();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Refreshing Tour Route Drawing");
                mMap.clear();
                showTourRoute();
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resuming Tour UI");
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(LOCATION_DATA_ACTION);
        if (mLocationDataReceiver != null) {
            Log.i(TAG, "REGISTERING RECEIVER");
            registerReceiver(mLocationDataReceiver, intentFilter);
        }
        // Refresh next tour stop if reached tour stop while backgrounded
        if (localNextStopIndex != LocationTrackingService.getNextStopIndex()) {
            refreshNextStop();
        }
    }

    @Override
    protected void onPause() {
        if (mLocationDataReceiver != null) {
            Log.i(TAG, "UNREGISTERING RECEIVER");
            unregisterReceiver(mLocationDataReceiver);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Destroy...");
        stopService(new Intent(this, LocationTrackingService.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapEventListeners();

        LatLng tourCoordinates = new LatLng(mTour.getLat(), mTour.getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tourCoordinates));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        showUserLocation();
        showTourRoute();
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
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                openPlaceInfo((Integer) marker.getTag());
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

                // Start location tracking service
                Intent locationIntent = new Intent(this, LocationTrackingService.class);
                String tourStopData[] = new String[mTourLocations.size()];

                for (int i = 0; i < mTourLocations.size(); i++) {
                    Location location = mTourLocations.get(i);
                    tourStopData[i] = location.getLatitude() + "," + location.getLongitude();
                }
                locationIntent.putExtra(TourActivity.TOUR_STOP_DATA, tourStopData);

                startService(locationIntent);
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
                        // Draw path to tour stops not visited yet
                        tourStops.addAll(mTourPlaces.subList(localNextStopIndex, mTourPlaces.size()));
                        DirectionsUtil.drawTourRoute(mMap, tourStops);
                        // Draw a marker for all tour stops even if visted
                        DirectionsUtil.drawTourMarkers(mMap, mTourPlaces);
                    }
                });
            } catch (SecurityException e) {
                // this catch is just here cause my IDE wasn't detecting the permission check
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * Check the LocationTrackingService to get the next tour stop the user should be going to, and
     * update the UI accordingly.
     */
    private void refreshNextStop() {
        int nextStopIdx = LocationTrackingService.getNextStopIndex();
        localNextStopIndex = nextStopIdx;

        if (nextStopIdx > 0) {
            int arrivedPlaceId = nextStopIdx - 1;
            Place arrivedPlace = mTourPlaces.get(arrivedPlaceId);
            String notificationMessage = "Arrived at " + arrivedPlace.getName();

            Snackbar snackbar = Snackbar.make(findViewById(R.id.snackBarView), notificationMessage, 10000);
            snackbar.setAction("More Details", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Playing media for: " + arrivedPlace.getName());

                    if (arrivedPlace.getVideoFile().length() > 0) {
                        Bundle b = new Bundle();
                        b.putString("uri", arrivedPlace.getVideoFile());
                        VideoDialogFragment vidDialog = new VideoDialogFragment();
                        vidDialog.setTitle("Video for " + arrivedPlace.getName());
                        vidDialog.setArguments(b);
                        vidDialog.show(getFragmentManager(), "video");
                    } else if (arrivedPlace.getAudioFile().length() > 0) {
                        Bundle b = new Bundle();
                        b.putString("uri", arrivedPlace.getAudioFile());
                        AudioDialogFragment audioDialog = new AudioDialogFragment();
                        audioDialog.setTitle("Audio for " + arrivedPlace.getName());
                        audioDialog.setArguments(b);
                        audioDialog.show(getFragmentManager(), "audio");
                    } else {
                        openPlaceInfo(arrivedPlaceId);
                    }
                }
            });
            snackbar.show();

            if (nextStopIdx < mTourPlaces.size()) {
                updatePreview(nextStopIdx);
            } else {
                Log.i(TAG, "END OF TOUR!");
                mPreviewTitleView.setText("End of the Tour!");
                mPreviewDescriptionView.setText("You have reached the end of the tour!. Press back to go" +
                        "to the main menu to see other tours!");
            }
        }
    }

    private void updatePreview(int stopIndex) {
        Place tourStop = mTourPlaces.get(stopIndex);
        new DownloadImageTask(mPreviewImageView::setImageBitmap).execute(tourStop.getPictureFile());
        mPreviewTitleView.setText(tourStop.getName());
        mPreviewDescriptionView.setText(tourStop.getDescription());
    }

    private void openPlaceInfo(int placeIndex) {
        String placeId = mTourPlaces.get(placeIndex).getId();
        Intent intent = new Intent(TourActivity.this, PlaceInfoActivity.class);
        intent.putExtra(PLACE_ID, placeId);
        startActivity(intent);
    }

    private boolean needsRuntimePermission(String permission) {
        // Check the SDK version and whether the permission is already granted.
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
    }
}
