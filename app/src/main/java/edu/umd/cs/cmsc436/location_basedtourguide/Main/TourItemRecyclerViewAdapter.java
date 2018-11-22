package edu.umd.cs.cmsc436.location_basedtourguide.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.TourItemFragment.OnListFragmentInteractionListener;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.DownloadImageTask;

public class TourItemRecyclerViewAdapter extends RecyclerView.Adapter<TourItemRecyclerViewAdapter.ViewHolder> {

    private List<String> mValues;
    private final OnListFragmentInteractionListener mListener;

    TourItemRecyclerViewAdapter(List<String> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        DataStore.getInstance().registerDataChangeListener(() -> {
            TourItemRecyclerViewAdapter.this.mValues = DataStore.getInstance().getTourIDs();
            TourItemRecyclerViewAdapter.this.notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_touritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String tourID = mValues.get(position);
        Tour tour = DataStore.getInstance().getTour(tourID);

        holder.mItem = tourID;
        holder.mIdView.setText(tour.getName());
        holder.mContentView.setText(tour.getDescription());
        new DownloadImageTask(holder.mImageView::setImageBitmap).execute(tour.getPictureFile());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) mListener.onListFragmentPress(holder.mItem);
        });

        holder.mView.setOnLongClickListener(v -> {
            if (null != mListener) mListener.onListFragmentLongPress(holder.mItem);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mIdView;
        private final TextView mContentView;
        private final ImageView mImageView;
        private String mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
            mImageView = view.findViewById(R.id.imageView);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
