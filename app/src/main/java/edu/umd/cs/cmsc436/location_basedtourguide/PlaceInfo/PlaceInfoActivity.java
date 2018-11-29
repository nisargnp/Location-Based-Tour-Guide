package edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.AudioDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.VideoDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;

public class PlaceInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);


        String placeId = getIntent().getStringExtra(TourActivity.PLACE_ID);
        if (placeId == null) {
            throw new IllegalArgumentException("Need to pass an intent with extras to PlaceInfoActivity!");
        }

        mPlace = DataStore.getInstance().getPlace(placeId);
        Log.d("testing", "placeInfo: " + mPlace.toString());

        ImageView placeImg = findViewById(R.id.place_img);
        TextView placeDesc = findViewById(R.id.place_desc);
        TextView placeName = findViewById(R.id.place_name);
        ImageButton videoButton = findViewById(R.id.vid_button);
        ImageButton audioButton = findViewById(R.id.audio_button);

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        placeDesc.setText(mPlace.getDescription());
        placeDesc.setMovementMethod(new ScrollingMovementMethod());

        placeName.setText(mPlace.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            placeName.setTooltipText(mPlace.getName());
        } else {
            placeName.setOnLongClickListener(v -> {
                Toast.makeText(PlaceInfoActivity.this, mPlace.getName(), Toast.LENGTH_LONG).show();
                return true;
            });
        }

        if (mPlace.getAudioFile() == null || mPlace.getAudioFile().length() == 0) {
            audioButton.setEnabled(false);
            audioButton.setColorFilter(new ColorMatrixColorFilter(matrix));
        } else {
            audioButton.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("uri", mPlace.getAudioFile());
                AudioDialogFragment audioDialog = new AudioDialogFragment();
                audioDialog.setArguments(b);
                audioDialog.show(getFragmentManager(), "audio");
            });
        }

        if (mPlace.getVideoFile() == null || mPlace.getVideoFile().length() == 0) {
            videoButton.setEnabled(false);
            videoButton.setColorFilter(new ColorMatrixColorFilter(matrix));
        } else {
            videoButton.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("uri", mPlace.getVideoFile());
                VideoDialogFragment vidDialog = new VideoDialogFragment();
                vidDialog.setArguments(b);
                vidDialog.show(getFragmentManager(), "video");
            });
        }

        new DownloadImageTask(placeImg::setImageBitmap).execute(mPlace.getPictureFile());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //if (mapFragment != null)
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;

        LatLng placeCoords = new LatLng(mPlace.getLat(), mPlace.getLon());
        map.moveCamera(CameraUpdateFactory.newLatLng(placeCoords));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));

        Marker marker = map.addMarker(new MarkerOptions().position(placeCoords).title(mPlace.getName()));
        marker.showInfoWindow();
    }

}
