package com.example.medicalapp.ui.pandemi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
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

public class KasusPerProvinsiActivity extends AppCompatActivity {
    //UI Elements
    private TableLayout mTableLayout;
    private ProgressBar mProgressBar;

    //Variables
    private static final String TAG = "Kasus Per Provinsi Act";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_per_provinsi);

        mTableLayout = findViewById(R.id.table_kasus_per_provinsi);
        mProgressBar = findViewById(R.id.progress_bar);

        getPerProvinsiData();
    }

    private void getPerProvinsiData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.kawalcorona.com/indonesia/provinsi/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressBar.setVisibility(View.GONE);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    setContent(jsonArray);
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

    private void setContent(JSONArray jsonArray){
        try{
            View tableHeader = getLayoutInflater().inflate(R.layout.table_provinsi_header, null, false);
            mTableLayout.addView(tableHeader);
            for (int i = 0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("attributes");

                View tableRow = getLayoutInflater().inflate(R.layout.table_provinsi_row, null, false);
                TextView provinsi = tableRow.findViewById(R.id.text_provinsi);
                TextView positifDakit = tableRow.findViewById(R.id.text_provinsi_positif_sakit);
                TextView sembuh = tableRow.findViewById(R.id.text_provinsi_sembuh);
                TextView meninggal = tableRow.findViewById(R.id.text_provinsi_meninggal);

                provinsi.setText(jsonObject.getString("Provinsi"));
                positifDakit.setText(jsonObject.getString("Kasus_Posi"));
                sembuh.setText(jsonObject.getString("Kasus_Semb"));
                meninggal.setText(jsonObject.getString("Kasus_Meni"));
                if (i % 2 == 1){
                    tableRow.setBackgroundColor(ContextCompat.getColor(this, R.color.white_blue));
                }
                mTableLayout.addView(tableRow);

                Log.d(TAG, "setContent: ");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
