package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder>
{

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView mIdView;
        public final TextView mContentView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            image.setVisibility(View.VISIBLE);
            mIdView = (TextView) itemView.findViewById(R.id.item_number);
            mContentView = (TextView) itemView.findViewById(R.id.content);

        }
    }


    private List<Place> places;

    public PlacesAdapter(List<Place> placesIn) {
        places = placesIn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Place place = places.get(i);
        viewHolder.mContentView.setText(place.getDescription());
        viewHolder.mIdView.setText(place.getName());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }





}
