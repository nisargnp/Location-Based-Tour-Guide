package edu.umd.cs.cmsc436.location_basedtourguide.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.Main.TourItemFragment.OnListFragmentInteractionListener;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Utils;

public class TourItemRecyclerViewAdapter extends RecyclerView.Adapter<TourItemRecyclerViewAdapter.ViewHolder> {

    private final List<Tour> mValues;
    private final OnListFragmentInteractionListener mListener;

    TourItemRecyclerViewAdapter(List<Tour> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mImageView.setImageBitmap(Utils.getImageFromInternalStorage(mValues.get(position).getPictureFile()));

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
        private Tour mItem;

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
