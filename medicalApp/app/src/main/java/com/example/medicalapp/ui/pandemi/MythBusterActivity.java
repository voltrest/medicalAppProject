package com.example.medicalapp.ui.pandemi;

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
import com.example.medicalapp.adapters.MythBusterAdapter;
import com.example.medicalapp.models.Myth;
import com.example.medicalapp.models.Penyakit;

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
    private MythBusterAdapter mMythBusterAdapter;
    private ArrayList<Integer> mColorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myth_buster);

        mColorList.add(ContextCompat.getColor(this, R.color.green));
        mColorList.add(ContextCompat.getColor(this, R.color.sea_green));
        mColorList.add(ContextCompat.getColor(this, R.color.steel_blue));
        mColorList.add(ContextCompat.getColor(this, R.color.royal_blue));
        mColorList.add(ContextCompat.getColor(this, R.color.slate_blue));
        mColorList.add(ContextCompat.getColor(this, R.color.dark_orchid));
        mColorList.add(ContextCompat.getColor(this, R.color.deep_pink));
        mColorList.add(ContextCompat.getColor(this, R.color.peru));
        mColorList.add(ContextCompat.getColor(this, R.color.crimson));
        mColorList.add(ContextCompat.getColor(this, R.color.dark_orange));
        mColorList.add(ContextCompat.getColor(this, R.color.gold));

        mRecyclerView = findViewById(R.id.recycler_myth);
        initRecyclerView();

        mProgressBar = findViewById(R.id.progress_bar);

        getData("http://192.168.1.14/get_myth_buster.php");
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMythBusterAdapter = new MythBusterAdapter(mMythList, mColorList);
        mRecyclerView.setAdapter(mMythBusterAdapter);
    }

    private void getData(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {
            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
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
                    //creating a URL
                    URL url = new URL(urlWebService);
                    //Opening the URL using HttpURLConnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    int status = conn.getResponseCode();
                    Log.i("tag", "getData: connection response code = " + status);

                    //StringBuilder object to read the string from the service
                    StringBuilder stringBuilder = new StringBuilder();
                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //A simple string to read values from each line
                    String json;
                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {
                        //appending it to string builder
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
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            mMythList.add(new Myth(obj.getString("id_myth"),
                    obj.getString("pertanyaan"),
                    obj.getString("jawaban")));
        }
        mMythBusterAdapter.notifyDataSetChanged();
    }
}
