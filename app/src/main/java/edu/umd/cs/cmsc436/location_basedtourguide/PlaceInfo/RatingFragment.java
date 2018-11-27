package edu.umd.cs.cmsc436.location_basedtourguide.PlaceInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class RatingFragment extends DialogFragment {
    TextView ratingText;
    TextView commentDesc;
    TextView nameDesc;
    RatingBar rating;
    Button submitButton;
    EditText commentBox;
    EditText nameText;
    // for upload
    private String tourId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View host = inflater.inflate(R.layout.fragment_rating, null);
        builder.setView(host);

        ratingText = host.findViewById(R.id.textView);
        rating = host.findViewById(R.id.ratingbar);
        submitButton = host.findViewById(R.id.submit_button);
        commentDesc = host.findViewById(R.id.comment_desc);
        commentDesc.setText("Add a comment:");
        commentBox = host.findViewById(R.id.comment_box);
        commentBox.setHint("Comment on your experience (optional)");
        nameDesc = host.findViewById(R.id.name_desc);
        nameDesc.setText("Name: ");
        nameText = host.findViewById(R.id.name_box);
        nameText.setHint("Enter name (optional)");
        ratingText.setText("Rate Tour!");
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText(getString(R.string.rating_string, rating));
            }
        });
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating, container, false);

    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }
}
