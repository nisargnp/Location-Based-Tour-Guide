package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.Serializable;

public class AddDetailsActivity extends AppCompatActivity {

    Button galleryButton;
    Button cameraButton;
    Button addButton;
    Button audioButton;
    Button videoButton;
    TextView stopTitle;
    TextView stopDescription;
    ImageView stopImage;
    private Uri selectedImage;
    private String imagePathFilename;

    private static final int USE_CAMERA = 0;
    private static final int CHOOSE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        galleryButton = findViewById(R.id.stop_gallery);
        cameraButton = findViewById(R.id.stop_camera);
        stopImage = findViewById(R.id.stop_image);
        stopDescription = findViewById(R.id.stop_description);
        stopTitle = findViewById(R.id.stop_title);
        addButton = findViewById(R.id.add_stop);
        audioButton = findViewById(R.id.audio_button);
        videoButton = findViewById(R.id.video_button);


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

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng l = (LatLng) getIntent().getExtras().getParcelable("LatLng");



                Intent output = new Intent();

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", l.latitude);
                bundle.putDouble("lon", l.longitude);
                bundle.putString("title", stopTitle.getText().toString());
                bundle.putString("description", stopDescription.getText().toString());
                bundle.putString("imageFilePath", imagePathFilename);
                //bundle.putString("videoFilePath", );
                //bundle.putString("audioFilePath", );
                output.putExtras(bundle);


                setResult(RESULT_OK, output);
                finish();

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
                        stopImage.setImageBitmap(selectedImage);
                        imagePathFilename = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" ,selectedImage.toString());

                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        stopImage.setImageBitmap(selectedImage);
                        imagePathFilename = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" ,selectedImage.toString());
                    }
                }
                break;
        }
    }



}
