package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import java.security.InvalidParameterException;

import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;

public class LocationTrackingService extends Service {
    private static final String TAG = "location-service";
    private static int notificationId = 1;
    private static final long LOCATION_REQUEST_INTERVAL = 5000;
    private static final float MIN_DISTANCE_DELTA = 10f;
    /**
     * Radius of tour stops. If users enter this radius, they will be considered to be "at" a
     * tour stop. 100 meters ~= 330 feet
     */
    private static final float TOUR_STOP_RADUIS_METERS = 100;

    private LocationManager mLocationManager;
    private Location nextTourStop;

    public LocationTrackingService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "in start command");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            throw new InvalidParameterException("Need extras for LocationTrackingService start");
        }

        double nextStopLat = extras.getDouble(TourActivity.LATEST_LOCATION_LAT);
        double nextStopLon = extras.getDouble(TourActivity.LATEST_LOCATION_LON);

        nextTourStop = new Location("next-location");
        nextTourStop.setLatitude(nextStopLat);
        nextTourStop.setLongitude(nextStopLon);

        Intent notificationIntent = new Intent(this, TourActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // TODO - add channel - thats why toast message
        Notification notification = new Notification.Builder(this)
                .setContentTitle("title")
                .setContentText("text")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(notificationId++, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "in on create");

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_REQUEST_INTERVAL,
                    MIN_DISTANCE_DELTA,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.i(TAG, "New Location: " + location.getLatitude() + ", " + location.getLongitude());
                            if (nextTourStop != null) {
                                float metersToNextStop = location.distanceTo(nextTourStop);
                                Log.i(TAG, "Distance to next stop: " + metersToNextStop + " meters");
                                if (metersToNextStop < TOUR_STOP_RADUIS_METERS) {
                                    Log.i(TAG, "In range of next tour stop");
                                }
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            Log.e(TAG, "onStatusChanged: " + provider);
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Log.e(TAG, "onProviderEnabled: " + provider);
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Log.e(TAG, "onProviderDisabled: " + provider);
                        }
                    });
        } catch (SecurityException e) {
            // need to request this permission before using this service!
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "service ending...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // don't have to implement this?
        return null;
    }
}
