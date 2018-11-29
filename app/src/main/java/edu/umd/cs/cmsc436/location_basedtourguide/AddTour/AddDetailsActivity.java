package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
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
            public void onClick(View view) { uploadAudio(); }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideo();
            }
        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case USE_CAMERA:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        stopImage.setImageBitmap(selectedImage);
                        imagePathFilename = Utils.putImageToInternalStorage(getApplicationContext(), selectedImage, "images" ,selectedImage.toString());

                    }
                }
                break;
            case CHOOSE_PICTURE:
                if(resultCode == RESULT_OK){
                    if (imageReturnedIntent != null) {
                        Uri contentURI = imageReturnedIntent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                            stopImage.setImageBitmap(bitmap);
                            imagePathFilename = Utils.putImageToInternalStorage(getApplicationContext(), bitmap, "images" ,bitmap.toString());

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Image Upload unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case UPLOAD_VIDEO:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {
                        Uri uri = imageReturnedIntent.getData();
                        File file = new File(uri.getPath());
                        videoPathFilename = file.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Successfully uploaded video file", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "VideoPath: " + videoPathFilename);

                    }
                }
                break;
            case UPLOAD_AUDIO:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {
                        Uri uri = imageReturnedIntent.getData();
                        File file = new File(uri.getPath());
                        audioPathFilename = file.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Successfully uploaded audio file", Toast.LENGTH_SHORT).show();
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




    public String getVideoPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private String getAudioPath(Uri uri) {
        String[] data = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
