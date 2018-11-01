package edu.umd.cs.cmsc436.location_basedtourguide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;
public class RatingActivity extends Activity {
    TextView ratingText;
    TextView commentDesc;
    TextView nameDesc;
    RatingBar rating;
    Button submitButton;
    EditText commentBox;
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingText = findViewById(R.id.textView);
        rating = findViewById(R.id.ratingbar);
        submitButton = findViewById(R.id.submit_button);
        commentDesc = findViewById(R.id.comment_desc);
        commentDesc.setText("Add a comment:");
        commentBox = findViewById(R.id.comment_box);
        commentBox.setHint("Comment on your experience (optional)");
        nameDesc = findViewById(R.id.name_desc);
        nameDesc.setText("Name: ");
        nameText = findViewById(R.id.name_box);
        nameText.setHint("Enter name (optional)");
        ratingText.setText("Rate Tour!");
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText(getString(R.string.rating_string, rating));
            }
        });
    }
}
