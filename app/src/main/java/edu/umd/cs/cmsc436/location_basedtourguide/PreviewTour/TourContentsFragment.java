package edu.umd.cs.cmsc436.location_basedtourguide.PreviewTour;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.Data.DataStore.DataStore;
import edu.umd.cs.cmsc436.location_basedtourguide.Firebase.DTO.Place;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Util.Directions.DirectionsUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TourContentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TourContentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourContentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_PLACES = "places";
    private static final String ARG_LAT = "lat";
    private static final String ARG_LON = "lon";

    GoogleMap gmap;
    NestedScrollView scrollView;

    private String mDescription;
    private List<String> mPlaces;
    private double mLat, mLon;

    private OnFragmentInteractionListener mListener;

    public TourContentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param description Description of Tour.
     * @param places List of Place IDS.
     * @return A new instance of fragment TourContentsFragment.
     */
    public static TourContentsFragment newInstance(String description, List<String> places, double lat, double lon) {
        TourContentsFragment fragment = new TourContentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putStringArrayList(ARG_PLACES, new ArrayList<>(places));
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LON, lon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDescription = getArguments().getString(ARG_DESCRIPTION);
            mPlaces = getArguments().getStringArrayList(ARG_PLACES);
            mLat = getArguments().getDouble(ARG_LAT);
            mLon = getArguments().getDouble(ARG_LON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tour_contents, container, false);

        TextView descriptionView = v.findViewById(R.id.description_container);
        descriptionView.setText(mDescription);

        if(gmap == null){
            PreviewMapFragment mMap = ((PreviewMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            mMap.getMapAsync(googleMap -> {
                gmap = googleMap;
                gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gmap.getUiSettings().setZoomControlsEnabled(false);

                scrollView = getView().findViewById(R.id.scroll_container);

                ((PreviewMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).setListener(new PreviewMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }
                });

                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLon), 4.0f));

                List<Place> mTourPlaces = new ArrayList<>();
                for(String placeID : mPlaces){
                    Place place = DataStore.getInstance().getPlace(placeID);
                    mTourPlaces.add(place);
                }

                DirectionsUtil.drawTourRoute(gmap, mTourPlaces);
                // Draw a marker for all tour stops even if visted
                DirectionsUtil.drawTourMarkers(gmap, mTourPlaces);
            });
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
