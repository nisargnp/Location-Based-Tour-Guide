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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.umd.cs.cmsc436.location_basedtourguide.AddTour.AddTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataProvider.DataProvider;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour.PreviewTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;

public class MainActivity extends AppCompatActivity implements TourItemFragment.OnListFragmentInteractionListener {

    public static final String TOUR_LIST_TAG = "TOUR_LIST";
    public static final String TOUR_TAG = "TOUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: remove this eventually
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("testing");
        dbRef.setValue("testValue");

        // TODO: get this from firebase eventually
        DataStore.getInstance().addTours(DataProvider.getTours());
        DataStore.getInstance().addPlaces(DataProvider.getPlaces());
        DataStore.getInstance().addComments(DataProvider.getComments());
        DataStore.getInstance().addUsers(DataProvider.getUsers());

        TourItemFragment tourItemFragment = new TourItemFragment();
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
    public void onListFragmentPress(String tourID) {
        Intent intent = new Intent(MainActivity.this, PreviewTourActivity.class);
        intent.putExtra(TOUR_TAG, tourID);
        startActivity(intent);
    }

    // long press -> show dialog to start tour directly
    @Override
    public void onListFragmentLongPress(String tourID) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent = new Intent(MainActivity.this, TourActivity.class);
                    intent.putExtra(TOUR_TAG, tourID);
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
