package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.MalformedParametersException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;

public class LocationTrackingService extends Service {
    private static final String TAG = "location-service";
    private static final long LOCATION_REQUEST_INTERVAL = 5000;
    private static final float MIN_DISTANCE_DELTA = 10f; // 10 meters TODO - test IRL. Raise value? => less power
    /**
     * Radius of tour stops. If users enter this radius, they will be considered to be "at" a
     * tour stop. 100 meters ~= 330 feet
     */
    private static final float TOUR_STOP_RADIUS_METERS = 100; // TODO - test IRL.
    /**
     * Source of truth for which tour stop the user needs to go to next. Stored as 0 based index
     */
    private static int nextStopIndex = 0;
    private static int notificationId = 11151990;
    private static String channelId = "my_channel_01";

    private LocationManager mLocationManager;
    private List<Location> listTourStops;
    private Context mContext;
    private NotificationManager mNotificationManager;
    private LocationListener mGPSLocationListener;
    private LocationListener mNetworkLocationListener;

    public LocationTrackingService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            throw new InvalidParameterException("Need extras for LocationTrackingService start");
        }

        listTourStops = new ArrayList<>();
        nextStopIndex = 0;
        // setup tour stops to track relative location to
        String tourStops[] = extras.getStringArray(TourActivity.TOUR_STOP_DATA);
        for (int i = 0; i < tourStops.length; i++) {
            String latLon[] = tourStops[i].split(",");

            if (latLon.length != 2) {
                throw new MalformedParametersException("Bad location coordinates for tracking service");
            }

            Location newTourStop = new Location("Tour Stop #" + i);
            newTourStop.setLatitude(Double.parseDouble(latLon[0]));
            newTourStop.setLongitude(Double.parseDouble(latLon[1]));
            listTourStops.add(newTourStop);
        }

        /*
        Foreground service to allow location updates when applications is backgrounded.
        NOTE: You will receive location updates less frequently when application is backgrounded.
        one update per ~30 seconds
         */
        Notification notification = createNotificationBuilder().build();
        // TODO - do we want multiple notifications?
        startForeground(notificationId++, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Creating new LocationTrackingService");
        mContext = getApplicationContext();

        createNotificationChannel();

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            mGPSLocationListener = new CustomLocationListener();
            mNetworkLocationListener = new CustomLocationListener();

            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_REQUEST_INTERVAL,
                    MIN_DISTANCE_DELTA,
                    mGPSLocationListener);
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_REQUEST_INTERVAL,
                    MIN_DISTANCE_DELTA,
                    mNetworkLocationListener);

            Log.i(TAG, "Requested Location Updates");
        } catch (SecurityException e) {
            // Service assumes you have already received necessary permissions
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "LocationTrackingService ending...");
        super.onDestroy();
        nextStopIndex = 0;

        if (mLocationManager != null) {
            if (mGPSLocationListener != null) {
                mLocationManager.removeUpdates(mGPSLocationListener);
            }
            if (mNetworkLocationListener != null) {
                mLocationManager.removeUpdates(mNetworkLocationListener);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // don't have to implement this
        return null;
    }

    public static int getNextStopIndex() {
        return nextStopIndex;
    }

    /**
     * Send a message to the tour component indicating that the user arrived at a stop. If tour
     * component is not in the foreground, create a notification to inform the user. Tour component
     * will automatically get the latest nextTourStopIndex variable on resume to update.
     */
    private void arriveAtStop() {
        nextStopIndex += 1;

        final Intent restartMainActivityIntent = new Intent(mContext, TourActivity.class);
        restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.sendOrderedBroadcast(new Intent(TourActivity.LOCATION_DATA_ACTION), null,
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (getResultCode() != TourActivity.IS_ALIVE) {
                            final PendingIntent pendingIntent = PendingIntent.getActivity(
                                    mContext,
                                    0,
                                    restartMainActivityIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                            Notification.Builder notificationBuilder = createNotificationBuilder();

                            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                                    .setTicker("Reached the next tour stop")
                                    .setContentTitle("You've reached the next tour stop!")
                                    .setContentText("Click for more details!")
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent);

                            mNotificationManager.notify(notificationId++, notificationBuilder.build());

                            Toast.makeText(mContext,
                                    "you have arrived at a stop!!!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, null, 0, null, null);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = new NotificationChannel(
                    channelId,
                    "MyCustomChannel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is the channel description");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private Notification.Builder createNotificationBuilder() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(mContext, channelId);
        }

        return new Notification.Builder(mContext);
    }

    private class CustomLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "Got Location from provider: " + location.getProvider());
            if (nextStopIndex < listTourStops.size()) {
                float metersToNextStop = location.distanceTo(listTourStops.get(nextStopIndex));
                Log.i(TAG, "Distance to next stop: " + metersToNextStop + " meters");
                if (metersToNextStop < TOUR_STOP_RADIUS_METERS) {
                    arriveAtStop();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.w(TAG, "onStatusChanged: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.w(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.w(TAG, "onProviderDisabled: " + provider);
        }
    }
}
