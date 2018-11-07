package edu.umd.cs.cmsc436.location_basedtourguide.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.AddTour.AddTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour.PreviewTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DataProvider.DataProvider;

public class MainActivity extends AppCompatActivity implements TourItemFragment.OnListFragmentInteractionListener {

    public static final String TOUR_LIST_TAG = "TOUR_LIST";
    public static final String TOUR_TAG = "TOUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: get this from firebase eventually
        DataProvider.generateTourImages(getApplicationContext());
        List<Tour> tours = DataProvider.getTours();

        TourItemFragment tourItemFragment = new TourItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(TOUR_LIST_TAG, (Serializable)tours);
        tourItemFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, tourItemFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddTourActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Settings - Not yet implemented.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // short press -> go to preview
    @Override
    public void onListFragmentPress(Tour item) {
        Intent intent = new Intent(MainActivity.this, PreviewTourActivity.class);
        intent.putExtra(TOUR_TAG, item); // Serializable
        startActivity(intent);
    }

    // long press -> show dialog to start tour directly
    @Override
    public void onListFragmentLongPress(Tour item) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent = new Intent(MainActivity.this, TourActivity.class);
                    intent.putExtra(TOUR_TAG, item); // Serializable
                    startActivity(intent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to start the tour?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
