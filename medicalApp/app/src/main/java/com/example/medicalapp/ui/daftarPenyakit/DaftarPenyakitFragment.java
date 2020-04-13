package com.example.medicalapp.ui.daftarPenyakit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.Adapters.DaftarPenyakitAdapter;
import com.example.medicalapp.Models.Penyakit;
import com.example.medicalapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DaftarPenyakitFragment extends Fragment implements DaftarPenyakitAdapter.OnPenyakitListener {

    private DaftarPenyakitViewModel daftarPenyakitViewModel;

    private Activity mActivity;

    //UI Components
    ListView listView;
    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Variables
    String[] daftarPenyakit;
    private ArrayList<Penyakit> mDaftarPenyakit = new ArrayList<>();
    private DaftarPenyakitAdapter mDaftarPenyakitAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity =(Activity) context;
        }
        Log.i("tag", "onAttach completed");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        Log.i("tag", "onDetach completed");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        daftarPenyakitViewModel =
                ViewModelProviders.of(this).get(DaftarPenyakitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daftar_penyakit, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        daftarPenyakitViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        }
//        );
        listView = root.findViewById(R.id.list_penyakit);
        getJSON("http://192.168.1.21/get_daftar_penyakit.php");

        mRecyclerView = root.findViewById(R.id.recycler_penyakit);

        initRecyclerView();
        //insertData();
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new DaftarPenyakitAdapter();
//        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDaftarPenyakitAdapter = new DaftarPenyakitAdapter(mDaftarPenyakit, this);
        mRecyclerView.setAdapter(mDaftarPenyakitAdapter);
    }

    private void getJSON(final String urlWebService) {
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
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                try {
                    Log.i("tag", "onPostExecute: loadIntoRecyclerView attempting");
                    loadIntoRecyclerView(s);
                    Log.i("tag", "onPostExecute: loadIntoRecyclerView succeeded");
                    for (int i = 0; i < mDaftarPenyakit.size(); i++){
                        Log.i("tag", mDaftarPenyakit.get(i).getNamaPenyakit());
                    }
                } catch (JSONException e) {
                    Log.i("tag", "onPostExecute: loadIntoRecyclerView failed");
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
                    Log.i("tag", "getJSON: connection response code = " + status);

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
                    //finally returning the read string
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
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);
        //creating a string array for listview
        daftarPenyakit = new String[jsonArray.length()];
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            //getting the name from the json object and putting it inside string array
            daftarPenyakit[i] = obj.getString("nama_penyakit");

            mDaftarPenyakit.add(new Penyakit(obj.getString("id_penyakit"),
                    obj.getString("nama_penyakit"),
                    obj.getString("penjelasan_penyakit")));
        }
        mDaftarPenyakitAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPenyakitClick(int position) {
        Intent intent = new Intent(getActivity(), PenyakitActivity.class);
        intent.putExtra("penyakit_terpilih", mDaftarPenyakit.get(position));
        startActivity(intent);
    }
}