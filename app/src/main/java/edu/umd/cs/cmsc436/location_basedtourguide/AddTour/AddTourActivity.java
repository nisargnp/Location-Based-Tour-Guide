package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils.FirebaseUtils;
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

import static edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils.FirebaseUtils.uploadToFirebase;


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
                if (tour.getPlaces().size() == 0) {
                    Toast.makeText(getApplicationContext(), "Tour must have atleast one stop",  Toast.LENGTH_SHORT).show();
                } else if (tour.getPictureFile() == null) {
                    Toast.makeText(getApplicationContext(), "Tour must have picture", Toast.LENGTH_SHORT).show();
                } else {
                    tour.setName(titleTextView.getText().toString());
                    tour.setDescription(descriptionTextView.getText().toString());
                    tour.setPictureFile(imageFilePath);
                    User Admin = new User();
                    Admin.setName("Admin");
                    String admin = FirebaseUtils.uploadToFirebaseRaw(null, Admin);
                    tour.setAuthor(admin);
                    FirebaseUtils.uploadToFirebase(AddTourActivity.this, tour);
                    finish();
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
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_PICTURE);
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
                        Uri contentURI = imageReturnedIntent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                            tourImageView.setImageBitmap(bitmap);
                            imageFilePath = Utils.putImageToInternalStorage(getApplicationContext(), bitmap, "images" ,bitmap.toString());

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Image Upload unsuccessful", Toast.LENGTH_SHORT).show();
                        }
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("tourTitle", titleTextView.getText().toString());
        savedInstanceState.putString("tourDescription", descriptionTextView.getText().toString());
        savedInstanceState.putString("tourImage", imageFilePath);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageFilePath = (String) savedInstanceState.getString("tourImage");
        titleTextView.setText(savedInstanceState.getString("tourTitle"));
        descriptionTextView.setText(savedInstanceState.getString("tourDescription"));
    }


}
