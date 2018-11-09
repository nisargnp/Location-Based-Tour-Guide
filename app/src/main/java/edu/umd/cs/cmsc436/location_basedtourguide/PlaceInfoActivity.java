package edu.umd.cs.cmsc436.location_basedtourguide;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
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

import java.io.File;

public class PlaceInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    ImageView placeImg;
    TextView placeDesc;
    VideoView videoView;
    Button tmpVideoButton;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        placeImg = findViewById(R.id.place_img);
        placeDesc = findViewById(R.id.place_desc);
        videoView = findViewById(R.id.videoview);
        tmpVideoButton = findViewById(R.id.vid_button);

        tmpVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater inflater = getLayoutInflater();
                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceInfoActivity.this);

                builder.setTitle("Title").setView(inflater.inflate(R.layout.fragment_video, null));
                VideoView videoView = findViewById(R.id.videoview);
                Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.teapot);
                if (videoView != null) {
                    videoView.setVideoURI(uri);
                    videoView.start();
                }

                AlertDialog dialog = builder.create();
                dialog.show();*/
                VideoDialogFragment vidDialog = new VideoDialogFragment();
                vidDialog.show(getFragmentManager(), "video");
            }
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
