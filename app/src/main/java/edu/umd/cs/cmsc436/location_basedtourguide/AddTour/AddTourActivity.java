package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.User;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.Utils.FirebaseUtils;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;


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
                    Toast.makeText(getApplicationContext(), "Tour must have at least one stop",  Toast.LENGTH_SHORT).show();
                } else if (tour.getPictureFile() == null) {
                    Toast.makeText(getApplicationContext(), "Tour must have picture", Toast.LENGTH_SHORT).show();
                } else {
                    tour.setName(titleTextView.getText().toString());
                    tour.setDescription(descriptionTextView.getText().toString());
                    tour.setPictureFile(imageFilePath);
                    User admin = new User();
                    admin.setName("Admin");
                    String adminId = FirebaseUtils.uploadToFirebase(AddTourActivity.this, admin, null);
                    tour.setAuthor(adminId);
                    FirebaseUtils.uploadToFirebase(AddTourActivity.this, tour, AddTourActivity.this::finish);
//                    finish();
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
                        imageFilePath = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" , "image_" + System.currentTimeMillis());
                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Uri contentURI = imageReturnedIntent.getData();
                        tourImageView.setImageURI(contentURI);
                        imageFilePath = AddTourUtils.getPath(AddTourActivity.this, contentURI);
                        Toast.makeText(getApplicationContext(), "Successfully added image.", Toast.LENGTH_SHORT).show();
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
