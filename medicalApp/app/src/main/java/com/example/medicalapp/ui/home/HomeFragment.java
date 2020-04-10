package com.example.medicalapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.medicalapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    private Activity mActivity;
    ListView listView;

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
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listView = (ListView) root.findViewById(R.id.list_penyakit);
        getJSON("http://192.168.1.21/get_daftar_penyakit2.php");
        return root;
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                try {
                    Log.i("tag", "onPostExecute: loadIntoListView attempting");
                    loadIntoListView(s);
                    Log.i("tag", "onPostExecute: loadIntoListView succeeded");
                } catch (JSONException e) {
                    Log.i("tag", "onPostExecute: loadIntoListView failed");
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

    private void loadIntoListView(String json) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);
        //creating a string array for listview
        String[] penyakitArray = new String[jsonArray.length()];
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            //getting the name from the json object and putting it inside string array
            penyakitArray[i] = obj.getString("nama_penyakit");
        }

        //the array adapter to load data into list
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.daftar_penyakit_list_item, penyakitArray);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.daftar_penyakit_list_item, penyakitArray);

        //attaching adapter to listview
        listView.setAdapter(arrayAdapter);
    }
}