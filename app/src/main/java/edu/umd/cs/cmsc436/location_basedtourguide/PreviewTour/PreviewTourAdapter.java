package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Tour;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class PreviewTourAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private Tour mTour;

    public PreviewTourAdapter(FragmentManager fm, Context context, Tour mTour) {
        super(fm);
        this.context = context;
        this.mTour = mTour;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return TourContentsFragment.newInstance(mTour.getDescription(), mTour.getPlaces(),
                        mTour.getLat(), mTour.getLon());
            case 1:
                return PreviewLocFragment.newInstance(1, mTour.getPlaces());
            case 2:
                return CommentFragment.newInstance(1, mTour.getComments());
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return context.getString(R.string.preview_details_title);
            case 1:
                return context.getString(R.string.preview_locations_title);
            case 2:
                return context.getString(R.string.preview_comments_title);
            default:
                return null;
        }
    }
}
