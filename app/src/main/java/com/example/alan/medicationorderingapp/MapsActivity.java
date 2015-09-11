package com.example.alan.medicationorderingapp;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

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

        LatLng latLng = new LatLng(latitude, longitude);
        float zoom_level = 14;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom_level));

        new GetMarkers().execute(new API_Connector());

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);

                intent.putExtra("name", marker.getTitle());
                intent.putExtra("pharmacy id", marker.getSnippet());

                startActivity(intent);

                return true;
            }
        });
}

    /**
     *
     * @param jsonArray
     */
    private void setMapMarkers(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mMap.addMarker(new MarkerOptions()
                        .title(jsonObject.getString("name"))
                        .position(new LatLng(
                                jsonObject.getDouble("lat"),
                                jsonObject.getDouble("lng")
                        ))
                        .snippet(String.valueOf(jsonObject.getInt("pharmacy_id")))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharmacy_green_cross)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private class GetMarkers extends AsyncTask<API_Connector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(API_Connector... params) {

            return params[0].PlotGoogleMarkers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            setMapMarkers(jsonArray);
        }
    }
}
