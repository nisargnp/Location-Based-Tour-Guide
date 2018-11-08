package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;

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
                    List<Place> tourStops = new ArrayList<>();

                    Place terrapinRow = new Place();
                    terrapinRow.setName("Terrapin Row");
                    terrapinRow.setLat(38.980367);
                    terrapinRow.setLon(-76.942366);

                    Place CSIC = new Place();
                    CSIC.setName("Computer Science Inst...");
                    CSIC.setLat(38.990085);
                    CSIC.setLon(-76.936182);

                    Place stamp = new Place();
                    stamp.setName("Stamp Student Union");
                    stamp.setLat(38.987881);
                    stamp.setLon(-76.944855);

                    Place eppley = new Place();
                    eppley.setName("Eppley Recreational Center");
                    eppley.setLat(38.993635);
                    eppley.setLon(-76.945155);

                    // order defines how route is drawn
                    tourStops.add(terrapinRow);
                    tourStops.add(CSIC);
                    tourStops.add(stamp);
                    tourStops.add(eppley);

                    // TODO - replace this call with the passed in places and delete this test button
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