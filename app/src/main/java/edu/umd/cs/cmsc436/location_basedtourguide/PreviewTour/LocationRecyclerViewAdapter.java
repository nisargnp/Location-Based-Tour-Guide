package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.ImageLoader;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour.PreviewLocFragment.OnListFragmentInteractionListener;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private final OnListFragmentInteractionListener mListener;

    public LocationRecyclerViewAdapter(List<String> places, OnListFragmentInteractionListener listener) {
        mValues = places;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String locID = mValues.get(position);
        holder.mPlace = DataStore.getInstance().getPlace(locID);
        holder.mContentView.setText(holder.mPlace.getDescription());
        holder.mNameView.setText(holder.mPlace.getName());
        //new DownloadImageTask(holder.mImageView::setImageBitmap).execute(holder.mPlace.getPictureFile());
        ImageLoader.getInstance().loadBitmap(holder.mPlace.getPictureFile(), holder.mImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mPlace);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mContentView;
        public ImageView mImageView;
        public Place mPlace;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.place_name);
            mContentView = view.findViewById(R.id.content);
            mImageView = view.findViewById(R.id.imageView);
            mImageView.setVisibility(View.VISIBLE);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
