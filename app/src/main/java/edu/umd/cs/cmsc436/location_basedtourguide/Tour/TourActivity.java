package edu.umd.cs.cmsc436.location_basedtourguide.Tour;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class TourActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String TAG = "tour-activity";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        // Add a map to the MapView
//        MapFragment mMapFragment = MapFragment.newInstance();
//        mMapFragment.getMapAsync(this);
//
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.tourMapView, mMapFragment);
//        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicked button");
                DownloadTask task = new DownloadTask();
                task.execute("test p1", "test p2");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapEventListeners();

        // TODO - change this to zoom to user location (check for permissions)
        // TODO - add markers based on passed in tour
        LatLng collegePark = new LatLng(38.9897, -76.9378);
        mMap.addMarker(new MarkerOptions()
                .position(collegePark)
                .title("Marker in College Park")
                .snippet("Some description text")
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(collegePark));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    private void setMapEventListeners() {
        // TODO - on info window click, send to appropriate place detail activity
        // TODO - do anything besides default zoom and show info window on marker click?
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i(TAG, marker.getTitle() + "'s Info Window Clicked");
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, List<String>> {
        private static final String URI = "https://maps.googleapis.com/maps/api/directions/json?key=***REMOVED***&origin=38.980367,-76.942366&destination=38.990085,-76.936182";

        protected List<String> doInBackground(String... params) {
            String data = null;
            HttpURLConnection httpURLConnection = null;

//            try {
//                httpURLConnection = (HttpURLConnection) new URL(URI).openConnection();
//
//                InputStream in = new BufferedInputStream(
//                        httpURLConnection.getInputStream());
//
//                data = readStream(in);
//
//            } catch (MalformedURLException exception) {
//                Log.e(TAG, "MalformedURLException");
//            } catch (IOException exception) {
//                Log.e(TAG, "IOException");
//            } finally {
//                if (httpURLConnection != null) {
//                    httpURLConnection.disconnect();
//                }
//            }


            Log.i(TAG, "BG with " + params.length);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return parseJsonString("{\n" +
                    "   \"geocoded_waypoints\" : [\n" +
                    "      {\n" +
                    "         \"geocoder_status\" : \"OK\",\n" +
                    "         \"place_id\" : \"ChIJHx9IdZXGt4kR8JVrNL8Utts\",\n" +
                    "         \"types\" : [ \"establishment\", \"point_of_interest\" ]\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"geocoder_status\" : \"OK\",\n" +
                    "         \"place_id\" : \"ChIJk0-YgqPGt4kRQVx3VoNQD1E\",\n" +
                    "         \"types\" : [ \"premise\" ]\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"routes\" : [\n" +
                    "      {\n" +
                    "         \"bounds\" : {\n" +
                    "            \"northeast\" : {\n" +
                    "               \"lat\" : 38.99019010000001,\n" +
                    "               \"lng\" : -76.935\n" +
                    "            },\n" +
                    "            \"southwest\" : {\n" +
                    "               \"lat\" : 38.9800124,\n" +
                    "               \"lng\" : -76.9440946\n" +
                    "            }\n" +
                    "         },\n" +
                    "         \"copyrights\" : \"Map data ©2018 Google\",\n" +
                    "         \"legs\" : [\n" +
                    "            {\n" +
                    "               \"distance\" : {\n" +
                    "                  \"text\" : \"1.3 mi\",\n" +
                    "                  \"value\" : 2100\n" +
                    "               },\n" +
                    "               \"duration\" : {\n" +
                    "                  \"text\" : \"9 mins\",\n" +
                    "                  \"value\" : 557\n" +
                    "               },\n" +
                    "               \"end_address\" : \"Computer Science Instructional Center, 8169 Paint Branch Dr, College Park, MD 20742, USA\",\n" +
                    "               \"end_location\" : {\n" +
                    "                  \"lat\" : 38.99019010000001,\n" +
                    "                  \"lng\" : -76.9361863\n" +
                    "               },\n" +
                    "               \"start_address\" : \"4200 Guilford Drive, B1, College Park, MD 20740, United States\",\n" +
                    "               \"start_location\" : {\n" +
                    "                  \"lat\" : 38.9803,\n" +
                    "                  \"lng\" : -76.9423913\n" +
                    "               },\n" +
                    "               \"steps\" : [\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"144 ft\",\n" +
                    "                        \"value\" : 44\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"1 min\",\n" +
                    "                        \"value\" : 16\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.9800124,\n" +
                    "                        \"lng\" : -76.9427257\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Head \\u003cb\\u003esouthwest\\u003c/b\\u003e toward \\u003cb\\u003eGuilford Dr\\u003c/b\\u003e\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"{i|lF|xrtM?@BJDHTTXT\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9803,\n" +
                    "                        \"lng\" : -76.9423913\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"486 ft\",\n" +
                    "                        \"value\" : 148\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"2 mins\",\n" +
                    "                        \"value\" : 103\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.9809038,\n" +
                    "                        \"lng\" : -76.94399709999999\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eGuilford Dr\\u003c/b\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-right\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"ah|lF`{rtMKT]l@iBnC]h@\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9800124,\n" +
                    "                        \"lng\" : -76.9427257\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"0.3 mi\",\n" +
                    "                        \"value\" : 554\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"3 mins\",\n" +
                    "                        \"value\" : 150\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.9808446,\n" +
                    "                        \"lng\" : -76.93797579999999\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eKnox Rd\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003ePass by 7-Eleven (on the left in 0.3&nbsp;mi)\\u003c/div\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-right\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"sm|lF~bstMOPGICCEKEICICGACAGAG?EAEAE?G?QAWEyC?K?I?K@MLu@H]DS?[CmBAeAAw@A[?W?M?S?K@K@[B[@WL_BDe@B]Ba@PyB\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9809038,\n" +
                    "                        \"lng\" : -76.94399709999999\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"0.6 mi\",\n" +
                    "                        \"value\" : 911\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"3 mins\",\n" +
                    "                        \"value\" : 166\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.9884962,\n" +
                    "                        \"lng\" : -76.9350886\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eBaltimore Ave\\u003c/b\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-left\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"gm|lFj}qtMC]}B?_C@S?qBEeCQg@G[Eo@Kk@M_@MOCYGOESE[KIC}@Y[OWKsAo@o@[u@_@k@[OIi@[YQYQ_@UAASOYUCAAAECe@YqBgA\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9808446,\n" +
                    "                        \"lng\" : -76.93797579999999\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"0.1 mi\",\n" +
                    "                        \"value\" : 163\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"1 min\",\n" +
                    "                        \"value\" : 46\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.988599,\n" +
                    "                        \"lng\" : -76.9366397\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eCampus Dr\\u003c/b\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-left\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"c}}lFhkqtM[QI^ItAAX?R?J@L?H@JBV@NDRLv@\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9884962,\n" +
                    "                        \"lng\" : -76.9350886\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"0.1 mi\",\n" +
                    "                        \"value\" : 185\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"1 min\",\n" +
                    "                        \"value\" : 39\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.9901716,\n" +
                    "                        \"lng\" : -76.93728449999999\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003ePaint Branch Dr\\u003c/b\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-right\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"w}}lF~tqtM[LYLc@POFODo@TE@C@[Li@TGBE@I?I?I?S?\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.988599,\n" +
                    "                        \"lng\" : -76.9366397\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  },\n" +
                    "                  {\n" +
                    "                     \"distance\" : {\n" +
                    "                        \"text\" : \"312 ft\",\n" +
                    "                        \"value\" : 95\n" +
                    "                     },\n" +
                    "                     \"duration\" : {\n" +
                    "                        \"text\" : \"1 min\",\n" +
                    "                        \"value\" : 37\n" +
                    "                     },\n" +
                    "                     \"end_location\" : {\n" +
                    "                        \"lat\" : 38.99019010000001,\n" +
                    "                        \"lng\" : -76.9361863\n" +
                    "                     },\n" +
                    "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e at \\u003cb\\u003eStadium Dr\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eDestination will be on the right\\u003c/div\\u003e\",\n" +
                    "                     \"maneuver\" : \"turn-right\",\n" +
                    "                     \"polyline\" : {\n" +
                    "                        \"points\" : \"qg~lF~xqtMA{CA}@\"\n" +
                    "                     },\n" +
                    "                     \"start_location\" : {\n" +
                    "                        \"lat\" : 38.9901716,\n" +
                    "                        \"lng\" : -76.93728449999999\n" +
                    "                     },\n" +
                    "                     \"travel_mode\" : \"DRIVING\"\n" +
                    "                  }\n" +
                    "               ],\n" +
                    "               \"traffic_speed_entry\" : [],\n" +
                    "               \"via_waypoint\" : []\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"overview_polyline\" : {\n" +
                    "            \"points\" : \"{i|lF|xrtMBLZ^XTKT]l@iBnCm@z@KMO_@I[CYGoE?UNcANq@CiCEqD@y@ToD^_FC]}B?sC@qBEeCQcAM{AYo@QyA_@{By@eFgCmBiAoA}@q@a@mCyAI^ItAAl@@XF|@RjAu@ZsBt@oAf@a@D]?CyE\"\n" +
                    "         },\n" +
                    "         \"summary\" : \"Knox Rd and Baltimore Ave\",\n" +
                    "         \"warnings\" : [],\n" +
                    "         \"waypoint_order\" : []\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"status\" : \"OK\"\n" +
                    "}");
        }

        protected void onPostExecute(List<String> result) {
            Log.i(TAG, "post exec with res:\n" + result);
            ((TextView) findViewById(R.id.testTextView)).setText(result.get(0));
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, "Reading from JSON source");
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException");
                    }
                }
            }
            return data.toString();
        }

        private List<String> parseJsonString(String data) {
            String ROUTES_TAG = "routes";
            String LEGS_TAG = "legs";
            String STEPS_TAG = "steps";
            String START_LOCATION_TAG = "start_location";
            String END_LOCATION_TAG = "end_location";
            String LAT_TAG = "lat";
            String LNG_TAG = "lng";

            List<String> result = new ArrayList<>();

            Log.i(TAG, "Parsing data: " + data);

            try {
                // check status for err?
                JSONObject responseObject = (JSONObject) new JSONTokener(
                        data).nextValue();

                JSONArray routes = responseObject.getJSONArray(ROUTES_TAG);

                if (routes.length() > 0) {
                    // just use first route
                    JSONObject routeObject = routes.getJSONObject(0);

                    JSONArray legs = routeObject.getJSONArray(LEGS_TAG);

                    for (int legs_idx = 0; legs_idx < legs.length(); legs_idx++) {
                        JSONObject legObject = legs.getJSONObject(legs_idx);

                        JSONArray steps = legObject.getJSONArray(STEPS_TAG);

                        for (int steps_idx = 0; steps_idx < steps.length(); steps_idx++) {
                            JSONObject stepObject = steps.getJSONObject(steps_idx);
                            JSONObject startLocation = stepObject.getJSONObject(START_LOCATION_TAG);
                            JSONObject endLocation = stepObject.getJSONObject(END_LOCATION_TAG);

                            result.add(LAT_TAG + ":"
                                    + startLocation.get(LAT_TAG) + ","
                                    + LNG_TAG + ":"
                                    + startLocation.getString(LNG_TAG) + ";"
                                    + LAT_TAG + ":"
                                    + endLocation.get(LAT_TAG) + ","
                                    + LNG_TAG + ":"
                                    + endLocation.getString(LNG_TAG));
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
}