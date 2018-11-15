package edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo;
import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class TestRatingActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rating);
        FragmentManager fM = getFragmentManager();

        RatingFragment rF = (RatingFragment) fM.findFragmentById(R.id.rating_fragment_container);

        if (null == fM.findFragmentById(R.id.rating_fragment_container)) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fM
                    .beginTransaction();

            rF = new RatingFragment();

            // Add the fragment to the layout
            fragmentTransaction.add(R.id.rating_fragment_container,
                    rF);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

        }
    }
}
