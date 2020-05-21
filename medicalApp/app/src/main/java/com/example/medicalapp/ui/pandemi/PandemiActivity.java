package com.example.medicalapp.ui.pandemi;

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

public class PandemiActivity extends AppCompatActivity {
    //UI Components
    private TextView mPositifSakitText;
    private TextView mMeninggalText;
    private TextView mSembuhText;
    private Button mKasusProvinsiButton;
    private Button mMythBusterButton;
    private ProgressBar mProgressBar;

    //Variables
    private static final String TAG = "Pandemi Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandemi);

        mPositifSakitText = findViewById(R.id.text_positif_sakit);
        mMeninggalText = findViewById(R.id.text_meninggal);
        mSembuhText = findViewById(R.id.text_sembuh);

        mKasusProvinsiButton = findViewById(R.id.button_kasus_provinsi);
        mKasusProvinsiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PandemiActivity.this, KasusPerProvinsiActivity.class);
                startActivity(intent);
            }
        });

        mMythBusterButton = findViewById(R.id.button_myth_buster);
        mMythBusterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PandemiActivity.this, MythBusterActivity.class);
                startActivity(intent);
            }
        });

        mProgressBar = findViewById(R.id.progress_bar);

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

    private void getIndoData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/indonesia/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progress bar disappears
                mProgressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = jsonArray.getJSONObject(0);
                    mPositifSakitText.setText(jsonObject.getString("positif"));
                    mSembuhText.setText(jsonObject.getString("sembuh"));
                    mMeninggalText.setText(jsonObject.getString("meninggal"));
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
