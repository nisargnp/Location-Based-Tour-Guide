package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils.FirebaseUtils;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class AddPlacesActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {


    GoogleMap map;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlacesAdapter mAdapter;
    FloatingActionButton fab;
    LocationManager locationManager;
    Tour tour;
    ArrayList<LatLng> latlngs = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<>();


    private ArrayList<Place> places;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int ADD_STOP_DETAILS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        fab = findViewById(R.id.fab);
        tour = new Tour();
        places = new ArrayList<Place>();
        mAdapter = new PlacesAdapter(places);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        checkIfEmpty();
        mRecyclerView.setAdapter(mAdapter);


        //prepareFakeData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                ArrayList<String> pIDs = new ArrayList<>();
                for (Place p : places) {
                    pIDs.add(p.getId());
                }
                DataStore.getInstance().addPlaces(places);
                bundle.putStringArrayList("places", pIDs);
                Intent output = new Intent().putExtras(bundle);
                setResult(RESULT_OK, output);
                finish();
            }
        });

        MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        enableMyLocation(map);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        zoomInOnMyLocation();


        map.setOnMapLongClickListener(this);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Log.i("Adapter Position", Integer.toString(viewHolder.getAdapterPosition()));

                int remove = viewHolder.getAdapterPosition();

                //Remove swiped item from list and notify the RecyclerView
                mAdapter.onItemDismiss(viewHolder.getAdapterPosition());


                Marker temp = markers.get(remove);
                temp.remove();
                markers.remove(temp);
                latlngs.remove(remove);
                map.clear();
                createPolyLine();
                checkIfEmpty();
                Snackbar.make(mRecyclerView, "Delete successful", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                swap(fromPosition, toPosition);
                map.clear();
                createPolyLine();

                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void swap(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(markers, i, i + 1);
                Collections.swap(latlngs, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(markers, i, i - 1);
                Collections.swap(latlngs, i, i - 1);
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(AddPlacesActivity.this, AddDetailsActivity.class);
        intent.putExtra("LatLng", latLng);
        startActivityForResult(intent, ADD_STOP_DETAILS);
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_STOP_DETAILS) {
            if (resultCode == RESULT_OK) {
                Place place = new Place();
                Bundle bundle = data.getExtras();
                place.setName(bundle.getString("title"));
                place.setDescription(bundle.getString("description"));
                place.setLon(bundle.getDouble("lon"));
                place.setLat(bundle.getDouble("lat"));
                place.setPictureFile(bundle.getString("imageFilePath"));
                place.setAudioFile(bundle.getString("audioFilePath"));
                place.setVideoFile(bundle.getString("videoFilePath"));

                String id = FirebaseUtils.uploadToFirebase(AddPlacesActivity.this, place, null);
                place.setId(id);
                places.add(place);
                checkIfEmpty();
                mAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), place.getName() + " added to tour", Toast.LENGTH_LONG).show();
                Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLat(), place.getLon()))
                        .title(place.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                markers.add(newMarker);
                LatLng newestLatLng = new LatLng(place.getLat(), place.getLon());
                latlngs.add(newestLatLng);
                createPolyLine();

            }
        } else if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (resultCode == RESULT_OK) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                    zoomInOnMyLocation();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Location Services are not available currently", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void createPolyLine() {

        DirectionsUtil.drawTourRoute(map, places);
        DirectionsUtil.drawTourMarkers(map, places);

    }

    private void checkIfEmpty() {
        if (places.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void zoomInOnMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }





    public void prepareFakeData() {

        Place place1 = new Place();
        place1.setName("Place One");
        place1.setDescription("The First Place");
        Bitmap bitmapUMD = BitmapFactory.decodeStream(getApplicationContext().getResources().openRawResource(R.raw.umd));
        String path = Utils.putImageToInternalStorage(getApplicationContext(), bitmapUMD, "images", "umd.jpg");
        place1.setPictureFile(path);

        places.add(place1);

        Place place2 = new Place();
        place2.setName("Place Two");
        place2.setDescription("The Second Place");
        Bitmap bitmapNiagaraFalls = BitmapFactory.decodeStream(getApplicationContext().getResources().openRawResource(R.raw.niagara_falls));
        path = Utils.putImageToInternalStorage(getApplicationContext(), bitmapNiagaraFalls, "images", "niagara_falls.jpg");
        place2.setPictureFile(path);

        places.add(place2);

        Place place3 = new Place();
        place3.setName("Place Three");
        place3.setDescription("The Third Place");
        Bitmap bitmapGrandCanyon = BitmapFactory.decodeStream(getApplicationContext().getResources().openRawResource(R.raw.grand_canyon));
        path = Utils.putImageToInternalStorage(getApplicationContext(), bitmapGrandCanyon, "images", "grand_canyon.jpg");
        place3.setPictureFile(path);
        places.add(place3);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    zoomInOnMyLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Location Permission was not granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }





}
