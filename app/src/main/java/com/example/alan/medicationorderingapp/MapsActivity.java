package com.example.alan.medicationorderingapp;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    //private static final String SERVICE_URL = "http://10.0.2.2/webservice/google_connection.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        //Enable my location
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location myLocation = locationManager.getLastKnownLocation(provider);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();

        double McMullans_lat = 54.583169;
        double McMullans_lng = -5.945575;

        double Clear_lat = 54.590678;
        double Clear_lng = -5.949237;

        double Windsor_lat = 54.579687;
        double Windsor_lng = -5.950346;

        double Medicare_lat = 54.573082;
        double Medicare_lng = -5.958753;

        double Botanic_lat = 54.586390;
        double Botanic_lng = -5.932073;

        LatLng latLng = new LatLng(latitude, longitude);
        float zoom_level = 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom_level));

        mMap.addMarker(new MarkerOptions().position(new LatLng(McMullans_lat, McMullans_lng)).title("McMullans Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Clear_lat, Clear_lng)).title("Clear Group Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Windsor_lat, Windsor_lng)).title("Windsor Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Medicare_lat, Medicare_lng)).title("Medicare-Osborne Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Clear_lat, Clear_lng)).title("Clear Group Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Botanic_lat, Botanic_lng)).title("Botanic Pharmacy").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),Pop.class);

                intent.putExtra("name",marker.getTitle());

                startActivity(intent);

                return true;
            }
        });

//        new Thread(new Runnable() {
//            public void run() {
//               try {
//                    plotMarkersOnMap();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//          }
//        }).start();
    }

//    protected void plotMarkersOnMap() throws IOException {

//        HttpURLConnection conn = null;
//        final StringBuilder json = new StringBuilder();

//        try {
//            URL url = new URL(SERVICE_URL);
//            conn = (HttpURLConnection) url.openConnection();
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());

//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1) {
//                json.append(buff, 0, read);
//            }
//        } catch (IOException e) {
//            throw new IOException("Error connecting to service", e);
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    createMarkersFromJson(json.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

//    public void createMarkersFromJson(String json) throws JSONException {
//        JSONArray jsonArray = new JSONArray(json);
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObj = jsonArray.getJSONObject(i);

    //String name = jsonObj.getString("name");
    //double lat = jsonObj.getDouble("lat");
    //double lng = jsonObj.getDouble("lng");

    //mMap.addMarker(new MarkerOptions().title(name).position(new LatLng(lat,lng)));


//            mMap.addMarker(new MarkerOptions()
//                            .title(jsonObj.getString("name"))
//                            .position(new LatLng(
//                                    jsonObj.getJSONArray("lat").getDouble(0),
//                                    jsonObj.getJSONArray("lng").getDouble(1)
//                           ))
//            );
//        }
//    }
}
