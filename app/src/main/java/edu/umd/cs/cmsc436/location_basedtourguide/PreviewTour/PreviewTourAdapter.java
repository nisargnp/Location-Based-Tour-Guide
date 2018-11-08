package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class PreviewTourAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public PreviewTourAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TourContentsFragment();
            case 1:
                return new PreviewLocFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return context.getString(R.string.preview_details_title);
            case 1:
                return context.getString(R.string.preview_locations_title);
            default:
                return null;
        }
    }
}
