package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class PreviewTourActivity extends AppCompatActivity implements TourContentsFragment.OnFragmentInteractionListener, PreviewLocFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_tour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("test");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        float rating = 3f;

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);
        TextView ratingScore = findViewById(R.id.rating_score);
        ratingScore.setText(String.format("%s", rating));

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager = findViewById(R.id.fragment_container);
        PagerAdapter pagerAdapter = new PreviewTourAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // TODO: use tour object to display correct data
        Tour tour = (Tour) getIntent().getExtras().get(MainActivity.TOUR_TAG);
        if (tour != null) Toast.makeText(PreviewTourActivity.this, "Tour Name: " + tour.getName(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onListFragmentInteraction(LocationItem.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
