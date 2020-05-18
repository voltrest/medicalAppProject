package com.example.medicalapp.map;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private static final String TAG = "DataParser";
    private static WeakReference<Activity> mActivity;
    private boolean mError = false;

    private HashMap<String, String> getSingleHospital(JSONObject googlePlaceJSON){
        HashMap<String, String > hashMapHospital = new HashMap<>();
        String placeId = "-NA-";
        String name = "-NA-";
        String vicinity = "-NA-";
        String rating = "-NA-";
        String latitude;
        String longitude;
        String error = "-NA-";

        try {
            if (!googlePlaceJSON.isNull("place_id")){
                placeId = googlePlaceJSON.getString("place_id");
            }
            if (!googlePlaceJSON.isNull("name")){
                name = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity")){
                vicinity = googlePlaceJSON.getString("vicinity");
            }
            if (!googlePlaceJSON.isNull("rating")){
                rating = Double.toString(googlePlaceJSON.getDouble("rating"));
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");

            hashMapHospital.put("place_id", placeId);
            hashMapHospital.put("name", name);
            hashMapHospital.put("vicinity", vicinity);
            hashMapHospital.put("rating", rating);
            hashMapHospital.put("lat", latitude);
            hashMapHospital.put("lng", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMapHospital;
    }

    private List<HashMap<String, String>> getAllHospitals(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String>> nearbyHospitalsList = new ArrayList<>();
        HashMap<String, String> nearbyHospital = null;

        for (int i = 0; i < count; i++){
            try {
                nearbyHospital = getSingleHospital((JSONObject) jsonArray.get(i));
                //Should move this outside for loop?
                nearbyHospitalsList.add(nearbyHospital);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyHospitalsList;
    }

    public List<HashMap<String, String>> parse(String jsonData){
        String status;
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            status = jsonObject.getString("status");
            jsonArray = jsonObject.getJSONArray("results");
            if (status.equals("OK")){
                mError = false;
                if (jsonArray.length() == 0){
                    Toast.makeText(mActivity.get(), "No nearby hospitals found", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "parse: No nearby hospitals found");
                } else if (jsonArray.length() > 0){
                    Toast.makeText(mActivity.get(), "Successfully searched for nearby hospitals", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "parse: Successfully searched for nearby hospitals");
                }
            } else {
                mError = true;
                Toast.makeText(mActivity.get(), "An error occurred while obtaining the data from Google", Toast.LENGTH_LONG).show();
                Log.d(TAG, "parse: An error occurred while obtaining the data from Google");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllHospitals(jsonArray);
    }

    public static void setActivity(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    public boolean ismError() {
        return mError;
    }
}
