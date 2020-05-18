package com.example.medicalapp.ui.rumahSakit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.adapters.DaftarRumahSakitAdapter;
import com.example.medicalapp.adapters.HospitalInfoWindowAdapter;
import com.example.medicalapp.map.DataParser;
import com.example.medicalapp.map.UrlReader;
import com.example.medicalapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RumahSakitFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        DaftarRumahSakitAdapter.OnRumahSakitListener,
        GoogleMap.OnInfoWindowClickListener {

    //UI Elements
    private RumahSakitViewModel rumahSakitViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mSwitchViewButton;

    //Variables
    private Activity mActivity;
    private static final String TAG = "RumahSakitFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrentLocMarker;
    private static final int USER_LOCATION_REQUEST = 99;
    private double mLatitude;
    private double mLongitude;
    private int mProximityRadius = 10000;
    private List<HashMap<String, String>> mDaftarRumahSakit = new ArrayList<>();
    private DaftarRumahSakitAdapter mDaftarRumahSakitAdapter;
    private HashMap<Marker, String> mMarkerList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity =(Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rumahSakitViewModel = ViewModelProviders.of(this).get(RumahSakitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rumah_sakit, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        MapView mapView = root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        Button searchButton = root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNearbyHospitals();
            }
        });

        mSwitchViewButton = root.findViewById(R.id.switch_view_button);
        mSwitchViewButton.setText(getString(R.string.button_view_list));
        mSwitchViewButton.setVisibility(View.GONE);
        mSwitchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });

        mRecyclerView = root.findViewById(R.id.recycler_rumah_sakit);
        initRecyclerView();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        return root;
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDaftarRumahSakitAdapter = new DaftarRumahSakitAdapter(mDaftarRumahSakit, this);
        mRecyclerView.setAdapter(mDaftarRumahSakitAdapter);
        mRecyclerView.setVisibility(View.GONE);
    }

    public boolean checkUserLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, USER_LOCATION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, USER_LOCATION_REQUEST);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case USER_LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if (mGoogleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnInfoWindowClickListener(this);
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
//        lastLocation = location;
        if (mCurrentLocMarker != null){
            mCurrentLocMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        mCurrentLocMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        if (mGoogleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getNearbyHospitals(){
        class NearbyHospitalGetter extends AsyncTask<Object, String, String> {
            private String googlePlaceData;
            private GoogleMap mMap;

            @Override
            protected String doInBackground(Object... objects) {
                mMap = (GoogleMap) objects[0];
                String url = (String) objects[1];

                UrlReader urlReader = new UrlReader();

                try {
                    googlePlaceData = urlReader.ReadURL(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return googlePlaceData;
            }

            @Override
            protected void onPostExecute(String s) {
                DataParser dataParser = new DataParser();
                dataParser.setActivity(getActivity());
                List<HashMap<String, String>> tempList;
                tempList = dataParser.parse(s);

                if (!dataParser.ismError()){
                    mDaftarRumahSakit.clear();
                    mDaftarRumahSakit.addAll(tempList);
                    displayNearbyPlaces();
                }
            }
        }

        Object[] transferData = new Object[2];
        NearbyHospitalGetter nearbyHospitalGetter = new NearbyHospitalGetter();

        String url = getUrl(mLatitude, mLongitude);
        transferData[0] = mMap;
        transferData[1] = url;

        nearbyHospitalGetter.execute(transferData);
//        Toast.makeText(getActivity(), "Searching for nearby hospitals", Toast.LENGTH_SHORT).show();
    }

    private String getUrl(double latitude, double longitude){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=").append(latitude).append(",").append(longitude);
        googleURL.append("&radius=").append(mProximityRadius);
        googleURL.append("&type=hospital");
        googleURL.append("&sensor=true");
        googleURL.append("&key=").append(getString(R.string.google_places_key));

        Log.d(TAG, "url = " + googleURL.toString());

        return googleURL.toString();
    }

    private void displayNearbyPlaces(){
        for (int i = 0; i < mDaftarRumahSakit.size(); i ++){
            MarkerOptions markerOptions = new MarkerOptions();
//            Marker markerOptions;
            HashMap<String, String> hashMapHospital = mDaftarRumahSakit.get(i);
            String placeId = hashMapHospital.get("place_id");
            String name = hashMapHospital.get("name");
            String vicinity = hashMapHospital.get("vicinity");
            String rating = hashMapHospital.get("rating");
            double lat = Double.parseDouble(hashMapHospital.get("lat"));
            double lng = Double.parseDouble(hashMapHospital.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(name);
            markerOptions.snippet(placeId + "\n" + vicinity + "\n" + rating + "★");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(markerOptions);

//            Marker tempMarker = mMap.addMarker(markerOptions);
//            mMarkerList.put(tempMarker, placeId);
        }

        mMap.setInfoWindowAdapter(new HospitalInfoWindowAdapter(getActivity()));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        mDaftarRumahSakitAdapter.notifyDataSetChanged();
//        mRecyclerView.setVisibility(View.VISIBLE);
        mSwitchViewButton.setVisibility(View.VISIBLE);
    }

    private void switchView(){
        if (mRecyclerView.getVisibility() == View.GONE){
            mRecyclerView.setVisibility(View.VISIBLE);
            mSwitchViewButton.setText(getString(R.string.button_view_map));
        } else if (mRecyclerView.getVisibility() == View.VISIBLE){
            mRecyclerView.setVisibility(View.GONE);
            mSwitchViewButton.setText(getString(R.string.button_view_list));
        }
    }

    @Override
    public void onRumahSakitClick(int position) {
        Intent intent = new Intent(getActivity(), DetailRumahSakitActivity.class);
//        intent.putExtra("rumah_sakit_terpilih", mDaftarRumahSakit.get(position));
        Log.d(TAG, "onRumahSakitClick: PLACE ID : " + mDaftarRumahSakit.get(position).get("place_id"));
        intent.putExtra("place_id", mDaftarRumahSakit.get(position).get("place_id"));
        if (mDaftarRumahSakit.get(position) == null){
            Log.d(TAG, "onRumahSakitClick: NULLLLLLLLL");
        } else {
            Log.d(TAG, "onRumahSakitClick: not NULLLLLLLLL");
        }
        startActivity(intent);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getActivity(), DetailRumahSakitActivity.class);
        String placeId = marker.getSnippet().substring(0, marker.getSnippet().indexOf("\n"));
        Log.d(TAG, "onInfoWindowClick: PLACE ID : " + placeId);
        intent.putExtra("place_id", placeId);
//        if (mDaftarRumahSakit.get(position) == null){
//            Log.d(TAG, "onRumahSakitClick: NULLLLLLLLL");
//        } else {
//            Log.d(TAG, "onRumahSakitClick: not NULLLLLLLLL");
//        }
        Toast.makeText(getActivity(), "Pemission saaaaaaaaaaa", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}