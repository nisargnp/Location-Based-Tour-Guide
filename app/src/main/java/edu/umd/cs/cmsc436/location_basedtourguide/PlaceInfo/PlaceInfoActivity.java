package edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.AudioDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.VideoDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class PlaceInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    ImageView placeImg;
    TextView placeDesc;
    VideoView videoView;
    Button tmpVideoButton;
    Button tmpAudioButton;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        placeImg = findViewById(R.id.place_img);
        placeDesc = findViewById(R.id.place_desc);
        videoView = findViewById(R.id.videoview);
        tmpVideoButton = findViewById(R.id.vid_button);
        tmpAudioButton = findViewById(R.id.audio_button);

        // TODO: these paths will come from the Tour object
        String audioPath = Utils.copyResourceToInternalStorage(getApplicationContext(), R.raw.posin, "test", "posin");
        String videoPath = Utils.copyResourceToInternalStorage(getApplicationContext(), R.raw.teapot, "test", "teapot");

        tmpVideoButton.setOnClickListener(v -> {
            // Test uri of video to play
            Bundle b = new Bundle();
            b.putString("uri", videoPath);

            VideoDialogFragment vidDialog = new VideoDialogFragment();
            vidDialog.setArguments(b);
            vidDialog.show(getFragmentManager(), "video");
        });

        tmpAudioButton.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("uri", audioPath);
            AudioDialogFragment audioDialog = new AudioDialogFragment();
            audioDialog.setArguments(b);
            audioDialog.show(getFragmentManager(), "audio");
        });
        placeDesc.setText("This is a historic place to be remembered! It indeed is!");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //if (mapFragment != null)
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
