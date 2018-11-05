package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddDetailsActivity extends AppCompatActivity {

    Button galleryButton;
    Button cameraButton;
    Button addButton;
    TextView stopTitle;
    TextView stopDescription;
    ImageView stopImage;

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

        galleryButton.setOnClickListener(new View.OnClickListener() {
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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent output = new Intent();
                output.putExtra("Stop Title", stopTitle.getText().toString());
                output.putExtra("Stop Description", stopDescription.getText().toString());
                setResult(RESULT_OK, output);
                finish();

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
                    stopImage.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    stopImage.setImageURI(selectedImage);
                }
                break;
        }
    }



}
