package edu.umd.cs.cmsc436.location_basedtourguide.Util.Location;

import android.annotation.TargetApi;
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

import java.security.InvalidParameterException;

import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;
import edu.umd.cs.cmsc436.location_basedtourguide.Tour.TourActivity;

public class LocationTrackingService extends Service {
    private static final String TAG = "location-service";
    private static int notificationId = 11151990;
    private static String channelId = "my_channel_01";
    private static final long LOCATION_REQUEST_INTERVAL = 5000;
    private static final float MIN_DISTANCE_DELTA = 10f;
    /**
     * Radius of tour stops. If users enter this radius, they will be considered to be "at" a
     * tour stop. 100 meters ~= 330 feet
     */
    private static final float TOUR_STOP_RADIUS_METERS = 100;

    private LocationManager mLocationManager;
    private Location nextTourStop;
    private Context mContext;
    private NotificationManager mNotificationManager;
    private String tourId;

    public LocationTrackingService() {
        super();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "in start command");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            throw new InvalidParameterException("Need extras for LocationTrackingService start");
        }

        tourId = extras.getString(MainActivity.TOUR_TAG);

        Double nextStopLat = extras.getDouble(TourActivity.LATEST_LOCATION_LAT);
        Double nextStopLon = extras.getDouble(TourActivity.LATEST_LOCATION_LON);
        nextTourStop = new Location("next-location");
        updateNextStop(nextStopLat, nextStopLon);

        /*
        Foreground service to allow location updates when applications is backgrounded.
        NOTE: You will receive location updates less frequently when application is backgrounded.
        one update per ~30 seconds
         */
        Notification notification = new Notification.Builder(mContext, channelId).build();
        startForeground(notificationId++, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "in on create");

        mContext = getApplicationContext();

        createNotificationChannel();

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
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
                                if (metersToNextStop < TOUR_STOP_RADIUS_METERS) {
                                    Log.i(TAG, "In range of next tour stop");
                                    arriveAtStop();
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
            // Service assumes you have already received necessary permissions
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "service ending...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // don't have to implement this
        return null;
    }

    private void updateNextStop(Double lat, Double lon) {
        if (nextTourStop != null && lat != null || lon != null) {
            nextTourStop.setLatitude(lat);
            nextTourStop.setLongitude(lon);
        } else {
            throw new InvalidParameterException("Bad next tour stop location update");
        }
    }

    private void arriveAtStop() {
        final Intent restartMainActivityIntent = new Intent(mContext,
                TourActivity.class);
        restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // don't think we need this if we set the Tour Activity to "singleInstance"
//        restartMainActivityIntent.putExtra(MainActivity.TOUR_TAG, tourId);

        mContext.sendOrderedBroadcast(new Intent(TourActivity.LOCATION_DATA_ACTION), null,
                new BroadcastReceiver() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.O)
                    public void onReceive(Context context, Intent intent) {
                        if (getResultCode() != TourActivity.IS_ALIVE) {
                            final PendingIntent pendingIntent = PendingIntent.getActivity(
                                    mContext,
                                    0,
                                    restartMainActivityIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                            Notification.Builder notificationBuilder = new Notification.Builder(mContext, channelId)
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setTicker("Reached the next tour stop")
                                    .setContentTitle("You've reached the next tour stop!")
                                    .setContentText("Click for more details!")
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent);

                            mNotificationManager.notify(notificationId++, notificationBuilder.build());

                            Toast.makeText(mContext,
                                    "you have arrived at a stop!!!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext,
                                    "you have arrived at a stop...",
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
}
