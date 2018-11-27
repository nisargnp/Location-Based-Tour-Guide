package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
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
    private Uri selectedImage;
    private String imagePathFilename;
    private String audioPathFilename;
    private String videoPathFilename;
    private MediaRecorder mRecorder;
    private boolean recording;

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
            public void onClick(View view) {
                if (recording) {
                    stopRecordingAudio();
                } else {
                    startRecordingAudio();
                }
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoFromGallery();
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
            case 2:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {
                        Uri selectedImageUri = imageReturnedIntent.getData();
                        videoPathFilename = getPath(selectedImageUri);
                    }
                }
            case 3:
                if (resultCode == RESULT_OK) {
                    if (imageReturnedIntent != null) {

                    }
                }
        }
    }


    private void startRecordingAudio() {

        recording = true;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioPathFilename = getFilename();
        mRecorder.setOutputFile(audioPathFilename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Couldn't Prepare AudioPlayer", Toast.LENGTH_SHORT).show();

        }
        mRecorder.start();
        Toast.makeText(getApplicationContext(), "Started Recording", Toast.LENGTH_SHORT).show();



    }

    private String getFilename()
    {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath);

        if(!file.exists()){
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp3");
    }

    private void stopRecordingAudio() {


        if (null != mRecorder) {
            recording = false;
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            Toast.makeText(getApplicationContext(), "Stopped Recording", Toast.LENGTH_SHORT).show();

        }
    }

    private void videoFromGallery() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),UPLOAD_VIDEO);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}
