package com.example.medicalapp.ui.pandemic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicalapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PandemicActivity extends AppCompatActivity {
    //UI Components
    private TextView mGlobalPositiveText;
    private TextView mGlobalCuredText;
    private TextView mGlobalDeathsText;
    private TextView mIndoPositiveText;
    private TextView mIndoDeathsText;
    private TextView mIndoCuredText;
    private Button mCasesProvinceButton;
    private Button mCasesCountryButton;
    private Button mMythBusterButton;
    private ProgressBar mIndonesiaProgressBar;
    private ProgressBar mGlobalProgressBar;

    //Variables
    private static final String TAG = "Pandemi Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandemic);

        mGlobalPositiveText = findViewById(R.id.text_dunia_positif);
        mGlobalCuredText = findViewById(R.id.text_dunia_sembuh);
        mGlobalDeathsText = findViewById(R.id.text_dunia_meninggal);

        mIndoPositiveText = findViewById(R.id.text_indo_positif);
        mIndoCuredText = findViewById(R.id.text_indo_sembuh);
        mIndoDeathsText = findViewById(R.id.text_indo_meninggal);

        mCasesProvinceButton = findViewById(R.id.button_kasus_provinsi);
        mCasesProvinceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PandemicActivity.this, CasesPerProvinceActivity.class);
                startActivity(intent);
            }
        });

        mCasesCountryButton = findViewById(R.id.button_kasus_negara);
        mCasesCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PandemicActivity.this, CasesPerCountryActivity.class);
                startActivity(intent);
            }
        });

        mMythBusterButton = findViewById(R.id.button_myth_buster);
        mMythBusterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PandemicActivity.this, MythBusterActivity.class);
                startActivity(intent);
            }
        });

        mIndonesiaProgressBar = findViewById(R.id.progress_bar_indonesia);
        mGlobalProgressBar = findViewById(R.id.progress_bar_dunia);

        getGlobalData();
        getIndoData();
    }

//    private void getData(){
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://api.kawalcorona.com/indonesia/";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //progress bar disappears
//                try {
////                    JSONArray jsonArray =
//                    JSONObject jsonObject = new JSONObject(response.toString());
//                    mPositifSakitText.setText(jsonObject.getString("positif"));
//                    mSembuhText.setText(jsonObject.getString("sembuh"));
//                    mMeninggalText.setText(jsonObject.getString("meninggal"));
//                    Log.e(TAG, "onResponse: Positif: " + jsonObject.getString("positif"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, "onErrorResponse: " + error.toString());
//            }
//        });
//
//        queue.add(stringRequest);
//    }

    private void getGlobalData(){
        getGlobalPositiveData();
        getGlobalCuredData();
        getGlobalDeathsData();
    }

    private void getGlobalPositiveData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/positif";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mGlobalPositiveText.setText(jsonObject.getString("value"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    private void getGlobalCuredData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/sembuh";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mGlobalCuredText.setText(jsonObject.getString("value"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    private void getGlobalDeathsData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/meninggal";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progress bar disappears
                mGlobalProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    mGlobalDeathsText.setText(jsonObject.getString("value"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        queue.add(stringRequest);
    }

    private void getIndoData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/indonesia/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mIndonesiaProgressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonArray.getJSONObject(0);
                    mIndoPositiveText.setText(jsonObject.getString("positif"));
                    mIndoCuredText.setText(jsonObject.getString("sembuh"));
                    mIndoDeathsText.setText(jsonObject.getString("meninggal"));
                    Log.e(TAG, "onResponse: Positif: " + jsonObject.getString("positif"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        queue.add(stringRequest);
    }
}
