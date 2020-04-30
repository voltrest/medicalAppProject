package com.example.medicalapp.map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getSingleHospital(JSONObject googlePlaceJSON){
        HashMap<String, String > googlePlaceMap = new HashMap<>();
        String nameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude;
        String longitude;
        String reference ;

        try {
            if (!googlePlaceJSON.isNull("name")){
                nameOfPlace = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity")){
                vicinity = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", nameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getAllHospitals(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String>> nearbyHospitalsList = new ArrayList<>();
        HashMap<String, String> nearbyPlaceMap = null;

        for (int i = 0; i < count; i++){
            try {
                nearbyPlaceMap = getSingleHospital((JSONObject) jsonArray.get(i));
                //Should move this outside for loop?
                nearbyHospitalsList.add(nearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyHospitalsList;
    }

    public List<HashMap<String, String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  getAllHospitals(jsonArray);
    }
}
