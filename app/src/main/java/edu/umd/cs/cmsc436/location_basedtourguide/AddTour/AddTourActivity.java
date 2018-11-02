package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class AddTourActivity extends AppCompatActivity {

    ImageView tourImageView;
    TextView titleTextView;
    TextView descriptionTextView;
    Button placesButton;
    Button createButton;
    Button galaryButton;
    Button cameraButton;

    private static final int USE_CAMERA = 0;
    private static final int CHOOSE_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);


        tourImageView = findViewById(R.id.tour_image);
        titleTextView = findViewById(R.id.tour_title);
        descriptionTextView = findViewById(R.id.tour_description);
        placesButton = findViewById(R.id.places_button);
        createButton = findViewById(R.id.create_button);
        galaryButton = findViewById(R.id.from_galery);
        cameraButton = findViewById(R.id.from_camera);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTourActivity.this, AddPlaces.class);
                startActivity(intent);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        galaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromGalery();
            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromCamera();
            }
        });

    }

    public void fromGalery() {

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
                    Uri selectedImage = imageReturnedIntent.getData();
                    tourImageView.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    tourImageView.setImageURI(selectedImage);
                }
                break;
        }
    }



}
