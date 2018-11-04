package edu.umd.cs.cmsc436.location_basedtourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.umd.cs.cmsc436.location_basedtourguide.AddTour.AddTourActivity;

public class MainActivity extends AppCompatActivity {

    Button addTourButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addTourButton = findViewById(R.id.addTourButton);

        addTourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTourActivity.class);
                startActivity(intent);
            }
        });
    }


}
