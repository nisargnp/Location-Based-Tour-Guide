package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

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
}