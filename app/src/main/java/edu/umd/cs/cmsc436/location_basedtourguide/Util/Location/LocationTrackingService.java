package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.security.InvalidParameterException;

public class LocationTrackingService extends Service {
    private static final String TAG = "location-service";
    private static final long LOCATION_REQUEST_INTERVAL = 5000;
    private static final long LOCATION_REQUEST_MIN_INTERVAL = 5000;
    /**
     * Radius of tour stops. If users enter this radius, they will be considered to be "at" a
     * tour stop. 100 meters ~= 330 feet
     */
    private static final float TOUR_STOP_RADUIS_METERS = 100;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    public LocationTrackingService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            throw new InvalidParameterException("Need extras for LocationTrackingService start");
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setFastestInterval(LOCATION_REQUEST_MIN_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        Log.i(TAG, location.getLatitude() + ", " + location.getLongitude());
                        // TODO - move into a service
                        // TODO - on arrival, notification if not active > autoplay > change preview view OR end fragment
                        // TODO - notification bar if app not active
//                        if (mNextStopIndex >= 0) {
//                            float metersToNextStop = location.distanceTo(mTourLocations.get(mNextStopIndex));
//                            Log.i(TAG, "Distance to next stop: " + metersToNextStop + " meters");
//                            if (metersToNextStop < TOUR_STOP_RADUIS_METERS) {
//                                nextTourStop();
//                            }
//                        }
                    }
                }

                ;
            }, null);
        } catch (SecurityException e) {
            // need to request this permission before using this service!
            Log.e(TAG, e.toString());
        }

        return START_STICKY;
    }

        @Override
    public IBinder onBind(Intent intent) {
        // don't have to implement this?
        return null;
    }

}
