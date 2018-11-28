package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class AddDetailsActivity extends AppCompatActivity {

    Button galleryButton;
    Button cameraButton;
    Button addButton;
    Button audioButton;
    Button videoButton;
    TextView stopTitle;
    TextView stopDescription;
    ImageView stopImage;
    private String imagePathFilename;
    private String audioPathFilename;
    private String videoPathFilename;


    private static final String TAG = "AddDetailsActivity";
    private static final int USE_CAMERA = 0;
    private static final int CHOOSE_PICTURE = 1;
    private static final int UPLOAD_VIDEO = 2;
    private static final int UPLOAD_AUDIO = 3;

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


        galleryButton.setOnClickListener(view -> fromGallery());
        cameraButton.setOnClickListener(view -> fromCamera());
        audioButton.setOnClickListener(view -> uploadAudio());
        videoButton.setOnClickListener(view -> uploadVideo());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imagePathFilename == null) {
                    Toast.makeText(getApplicationContext(), "Please add place photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                LatLng l = (LatLng) getIntent().getExtras().getParcelable("LatLng");

                Intent output = new Intent();

                Bundle bundle = new Bundle();
                bundle.putDouble("lat", l.latitude);
                bundle.putDouble("lon", l.longitude);
                bundle.putString("title", stopTitle.getText().toString());
                bundle.putString("description", stopDescription.getText().toString());
                bundle.putString("imageFilePath", imagePathFilename);
                bundle.putString("videoFilePath", videoPathFilename);
                bundle.putString("audioFilePath", audioPathFilename);
                output.putExtras(bundle);


                setResult(RESULT_OK, output);
                finish();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    if (data != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        stopImage.setImageBitmap(bitmap);
                        imagePathFilename = Utils.putImageToInternalStorage(getApplicationContext(), bitmap, "images" , "image_" + System.currentTimeMillis());
                    }
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    if (data != null) {
                        Uri uri = data.getData();
                        imagePathFilename = AddTourUtils.getPath(AddDetailsActivity.this, uri);
                        Toast.makeText(getApplicationContext(), "Successfully added image.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "ImagePath: " + imagePathFilename);
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        videoPathFilename = AddTourUtils.getPath(AddDetailsActivity.this, uri);
                        Toast.makeText(getApplicationContext(), "Successfully added video.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "VideoPath: " + videoPathFilename);
                    }
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getData();
                        audioPathFilename = AddTourUtils.getPath(AddDetailsActivity.this, uri);
                        Toast.makeText(getApplicationContext(), "Successfully added audio.", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "AudioPath: " + audioPathFilename);
                    }
                }
                break;
        }
    }

    private void uploadAudio(){
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Audio"),UPLOAD_AUDIO);

    }

    private void uploadVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),UPLOAD_VIDEO);
    }

}
