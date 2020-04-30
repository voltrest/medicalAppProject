package com.example.medicalapp.map;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class getNearbyHospitalGAGAL extends AsyncTask<Object, String, String> {
    private String googlePlaceData;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        String url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            googlePlaceData = downloadUrl.ReadTheURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
        List<HashMap<String, String>> nearbyHospitalsList = null;
        DataParser dataParser = new DataParser();
        nearbyHospitalsList = dataParser.parse(s);
        displayNearbyPlaces(nearbyHospitalsList);
    }

    private void displayNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList){
        for (int i = 0; i < nearbyPlacesList.size(); i ++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googleNearbyPlaces = nearbyPlacesList.get(i);
            String nameOfPlaces = googleNearbyPlaces.get("place_name");
            String vicinity = googleNearbyPlaces.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlaces.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlaces.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlaces + ": " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
