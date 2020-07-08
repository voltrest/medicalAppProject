package com.example.medicalapp.ui.pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.medicalapp.R;
import com.example.medicalapp.adapters.MythBusterRecyclerAdapter;
import com.example.medicalapp.models.Myth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MythBusterActivity extends AppCompatActivity {
    //UI Components
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    //Variables
    private ArrayList<Myth> mMythList = new ArrayList<>();
    private MythBusterRecyclerAdapter mMythBusterRecyclerAdapter;
    private ArrayList<Integer> mColorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myth_buster);

        mColorList.add(ContextCompat.getColor(this, R.color.green));
        mColorList.add(ContextCompat.getColor(this, R.color.seaGreen));
        mColorList.add(ContextCompat.getColor(this, R.color.steelBlue));
        mColorList.add(ContextCompat.getColor(this, R.color.royalBlue));
        mColorList.add(ContextCompat.getColor(this, R.color.slateBlue));
        mColorList.add(ContextCompat.getColor(this, R.color.darkOrchid));
        mColorList.add(ContextCompat.getColor(this, R.color.deepPink));
        mColorList.add(ContextCompat.getColor(this, R.color.peru));
        mColorList.add(ContextCompat.getColor(this, R.color.crimson));
        mColorList.add(ContextCompat.getColor(this, R.color.darkOrange));
        mColorList.add(ContextCompat.getColor(this, R.color.gold));

        mRecyclerView = findViewById(R.id.recycler_myth);
        initRecyclerView();

        mProgressBar = findViewById(R.id.progress_bar_indonesia);

        getData("http://192.168.1.14/get_myth_buster.php");
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMythBusterRecyclerAdapter = new MythBusterRecyclerAdapter(mMythList, mColorList);
        mRecyclerView.setAdapter(mMythBusterRecyclerAdapter);
    }

    private void getData(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            @Override
            protected void onPostExecute(String s) {
                mProgressBar.setVisibility(View.GONE);
                try {
                    if (s != null){
                        loadIntoRecyclerView(s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    int status = conn.getResponseCode();
                    Log.i("tag", "getData: connection response code = " + status);

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json + "\n");
                    }
                    return stringBuilder.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoRecyclerView(String json) throws JSONException {
        mMythList.clear();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            mMythList.add(new Myth(obj.getString("id_myth"),
                    obj.getString("pertanyaan"),
                    obj.getString("jawaban")));
        }
        mMythBusterRecyclerAdapter.notifyDataSetChanged();
    }
}
