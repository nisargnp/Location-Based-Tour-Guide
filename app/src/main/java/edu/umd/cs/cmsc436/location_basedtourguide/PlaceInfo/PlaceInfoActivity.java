package edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.AudioDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.AudioVideo.VideoDialogFragment;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataProvider.DataProvider;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;

public class PlaceInfoActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        // TODO: remove hardcode
        DataStore.getInstance().addTours(DataProvider.getTours());
        DataStore.getInstance().addPlaces(DataProvider.getPlaces());
        DataStore.getInstance().addComments(DataProvider.getComments());
        DataStore.getInstance().addUsers(DataProvider.getUsers());

        // TODO: remove hardcode
        String placeId = "-vQLJaqfPfLJgObaJpUst";
        Place place = DataStore.getInstance().getPlace(placeId);

        ImageView placeImg = findViewById(R.id.place_img);
        TextView placeDesc = findViewById(R.id.place_desc);
        Button videoButton = findViewById(R.id.vid_button);
        Button audioButton = findViewById(R.id.audio_button);

        placeDesc.setText(place.getDescription());

        if (place.getAudioFile().length() == 0) {
            audioButton.setEnabled(false);
        } else {
            audioButton.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("uri", place.getAudioFile());
                AudioDialogFragment audioDialog = new AudioDialogFragment();
                audioDialog.setArguments(b);
                audioDialog.show(getFragmentManager(), "audio");
            });
        }

        if (place.getVideoFile().length() == 0) {
            videoButton.setEnabled(false);
        } else {
            videoButton.setOnClickListener(v -> {
                Bundle b = new Bundle();
                b.putString("uri", place.getVideoFile());
                VideoDialogFragment vidDialog = new VideoDialogFragment();
                vidDialog.setArguments(b);
                vidDialog.show(getFragmentManager(), "video");
            });
        }

        new DownloadImageTask(placeImg::setImageBitmap).execute(place.getPictureFile());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //if (mapFragment != null)
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
