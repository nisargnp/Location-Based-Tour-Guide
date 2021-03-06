package edu.umd.cs.cmsc436.location_basedtourguide.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import edu.umd.cs.cmsc436.location_basedtourguide.AddTour.AddTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataGenerator.DataGenerator;
import edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour.PreviewTourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;

public class MainActivity extends AppCompatActivity implements TourItemFragment.OnListFragmentInteractionListener {

    public static final String TOUR_TAG = "TOUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: remove hardcode - generates firebase data
        if (!TourActivity.RUNNING_DEMO) {
            DataGenerator.generateDataFirebase();
        }

        // TODO: remove hardcode - generates local data (pre-firebase)
//        DataGenerator.generateDataLocal();
//        DataStore.getInstance().addTours(DataGenerator.getTours());
//        DataStore.getInstance().addPlaces(DataGenerator.getPlaces());
//        DataStore.getInstance().addComments(DataGenerator.getComments());
//        DataStore.getInstance().addUsers(DataGenerator.getUsers());

        TourItemFragment tourItemFragment = new TourItemFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, tourItemFragment); // use replace instead of add
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddTourActivity.class)));

        // TODO: example -> how to add data to firebase
//        fab.setOnClickListener(v -> {
//            String path = Utils.copyResourceToInternalStorage(MainActivity.this, R.raw.niagara_falls, "mImageView", "niagara_falls");
//            Tour tour = new Tour();
//            tour.setName("Test Place");
//            tour.setPictureFile(path);
//            tour.setDescription("Test adding a new place.");
//            FirebaseUtils.uploadToFirebase(MainActivity.this, tour, null);
//        });

        // TODO: example -> how to start rating fragment
//        fab.setOnClickListener(v -> {
//            RatingFragment ratingFragment = new RatingFragment();
//            Log.d("testing", "tour_id: " + DataStore.getInstance().getTours().get(0).getId());
//            ratingFragment.setTourId(DataStore.getInstance().getTours().get(0).getId());
//            ratingFragment.show(getSupportFragmentManager(), "rating");
//        });

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
