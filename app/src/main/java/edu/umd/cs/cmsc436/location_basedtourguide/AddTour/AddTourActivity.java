package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddTourActivity extends AppCompatActivity {

    ImageView tourImageView;
    TextView titleTextView;
    TextView descriptionTextView;
    Button placesButton;
    Button createButton;
    Button galleryButton;
    Button cameraButton;
    Tour tour;
    String imageFilePath;
    private Uri selectedImage;

    private static final int USE_CAMERA = 0;
    private static final int CHOOSE_PICTURE = 1;
    private static final int REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        tour = new Tour();
        tourImageView = findViewById(R.id.tour_image);
        titleTextView = findViewById(R.id.tour_title);
        descriptionTextView = findViewById(R.id.tour_description);
        placesButton = findViewById(R.id.places_button);
        createButton = findViewById(R.id.create_button);
        galleryButton = findViewById(R.id.from_gallery);
        cameraButton = findViewById(R.id.from_camera);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTourActivity.this, AddPlacesActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tour == null) {
                    Toast.makeText(getApplicationContext(), "Tour must have atleast one stop",  Toast.LENGTH_SHORT).show();
                } else {
                    tour.setName(titleTextView.getText().toString());
                    tour.setDescription(descriptionTextView.getText().toString());
                    tour.setPictureFile(imageFilePath);
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromGallery();
            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromCamera();
            }
        });

    }

    public void fromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    public void fromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, USE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        tourImageView.setImageBitmap(selectedImage);
                        imageFilePath = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" ,selectedImage.toString());

                    }
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        tourImageView.setImageBitmap(selectedImage);
                        imageFilePath = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" ,selectedImage.toString());

                    }
                }
                break;
            case 2:
                if(resultCode == RESULT_OK) {
                    Bundle bundle = imageReturnedIntent.getExtras();
                    tour.setPlaces(bundle.getStringArrayList("places"));
                }
        }
    }

}
