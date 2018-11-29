package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Comment;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo.PlaceInfoActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;

public class PreviewTourActivity extends AppCompatActivity implements TourContentsFragment.OnFragmentInteractionListener,
        PreviewLocFragment.OnListFragmentInteractionListener, CommentFragment.OnListFragmentInteractionListener{
    public static final String TOUR_TAG = "TOUR";
    protected Tour mTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String tourID = getIntent().getExtras().getString(MainActivity.TOUR_TAG);
        mTour = DataStore.getInstance().getTour(tourID);

        getSupportActionBar().setTitle(mTour.getName());

        ImageView imageView = findViewById(R.id.htab_header);

        new DownloadImageTask(imageView::setImageBitmap).execute(mTour.getPictureFile());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewTourActivity.this, TourActivity.class);
                intent.putExtra(TOUR_TAG, tourID);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        float rating = mTour.getRating() * 1.0f/mTour.getNumVotes();
        rating = rating == rating ? rating : 0; // NaN check

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);
        TextView ratingScore = findViewById(R.id.rating_score);

        DecimalFormat df = new DecimalFormat("#.##");
        ratingScore.setText(df.format(rating));

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager = findViewById(R.id.fragment_container);
        PagerAdapter pagerAdapter = new PreviewTourAdapter(getSupportFragmentManager(),
                this, this.mTour);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public Tour getTour(){
        return mTour;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Place place) {
        Intent intent = new Intent(PreviewTourActivity.this, PlaceInfoActivity.class);
        intent.putExtra(TourActivity.PLACE_ID, place.getId());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Comment item) {

    }
}
