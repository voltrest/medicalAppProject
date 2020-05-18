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

public class DetailRumahSakitParser {
    private static final String TAG = "DetailRumahSakitParser";
    private boolean mError = false;

    private HashMap<String, String> getDetail(JSONObject googlePlaceJSON){
        HashMap<String, String > hashMapHospital = new HashMap<>();
        String name = "-NA-";
        String rating = "-NA-";
        String userRatingsTotal = "-NA-";
        String address = "-NA-";
        String phoneNumber  = "-NA-";
        String website = "-NA-";
        String photoReference = "-NA-";
        String latitude;
        String longitude;
        String error = "-NA-";

        try {
            if (!googlePlaceJSON.isNull("name")){
                name = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("rating")){
                rating = Double.toString(googlePlaceJSON.getDouble("rating"));
            }
            if (!googlePlaceJSON.isNull("user_ratings_total")){
                userRatingsTotal = googlePlaceJSON.getString("user_ratings_total");
            }
            if (!googlePlaceJSON.isNull("formatted_address")){
                address = googlePlaceJSON.getString("formatted_address");
            }
            if (!googlePlaceJSON.isNull("formatted_phone_number")){
                phoneNumber = googlePlaceJSON.getString("formatted_phone_number");
            }
            if (!googlePlaceJSON.isNull("website")){
                website = googlePlaceJSON.getString("website");
            }
            if (!googlePlaceJSON.isNull("photos")){
                if (googlePlaceJSON.getJSONArray("photos").length() > 0){
                    JSONObject photo = (JSONObject) googlePlaceJSON.getJSONArray("photos").get(0);
                    photoReference = photo.getString("photo_reference");
                }
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");

            hashMapHospital.put("name", name);
            hashMapHospital.put("rating", rating);
            hashMapHospital.put("user_ratings_total", userRatingsTotal);
            hashMapHospital.put("address", address);
            hashMapHospital.put("phone_number", phoneNumber);
            hashMapHospital.put("website", website);
            hashMapHospital.put("photo_reference", photoReference);
            hashMapHospital.put("lat", latitude);
            hashMapHospital.put("lng", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMapHospital;
    }

    public HashMap<String, String> parse(String jsonData){
        String status;
        JSONObject jsonObject;
        JSONObject jsonHospital = null;

        try {
            jsonObject = new JSONObject(jsonData);
            status = jsonObject.getString("status");
            if (status.equals("OK")){
                jsonHospital = jsonObject.getJSONObject("result");
                mError = false;
                if (jsonHospital.length() == 0){
                    Log.e(TAG, "parse: No hospital details found");
                } else if (jsonHospital.length() > 0){
                    Log.d(TAG, "parse: Successfully obtained hospital detail");
                }
            } else {
                mError = true;
                Log.e(TAG, "parse: An error occurred while requesting the hospital detail from Google");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mError){
            return null;
        } else {
            return getDetail(jsonHospital);
        }
    }

    public boolean isError() {
        return mError;
    }
}
