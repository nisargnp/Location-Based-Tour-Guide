package edu.umd.cs.cmsc436.location_basedtourguide.AddTour;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> implements ItemTouchHelperAdapter
{

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView mIdView;
        public final TextView mContentView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            image.setVisibility(View.VISIBLE);
            mIdView = (TextView) itemView.findViewById(R.id.place_name);
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
                .inflate(R.layout.fragment_place_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Place place = places.get(i);
        viewHolder.mContentView.setText(place.getDescription());
        viewHolder.mIdView.setText(place.getName());
        viewHolder.image.setImageBitmap(Utils.getImageFromInternalStorage(place.getPictureFile()));

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(places, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(places, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        places.remove(position);
        notifyItemRemoved(position);
    }


}
