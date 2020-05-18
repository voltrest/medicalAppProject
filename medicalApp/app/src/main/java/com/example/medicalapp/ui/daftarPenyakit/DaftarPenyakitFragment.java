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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalapp.adapters.DaftarPenyakitAdapter;
import com.example.medicalapp.models.Penyakit;
import com.example.medicalapp.R;
import com.example.medicalapp.ui.daftarPenyakit.detailPenyakit.DetailPenyakitActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DaftarPenyakitFragment extends Fragment implements DaftarPenyakitAdapter.OnPenyakitListener {
//    private DaftarPenyakitViewModel daftarPenyakitViewModel;

    //UI Components
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    //Variables
    private Activity mActivity;
    private ArrayList<Penyakit> mDaftarPenyakit = new ArrayList<>();
    private DaftarPenyakitAdapter mDaftarPenyakitAdapter;
    private static final String TAG = "DaftarPenyakitFragment";

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

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getData("http://192.168.1.21/get_daftar_penyakit.php");
//
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        DaftarPenyakitViewModel daftarPenyakitViewModel = ViewModelProviders.of(this).get(DaftarPenyakitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daftar_penyakit, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_penyakit);
        initRecyclerView();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        getData("http://192.168.1.17/get_daftar_penyakit.php");
        return root;
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDaftarPenyakitAdapter = new DaftarPenyakitAdapter(mDaftarPenyakit, this);
        mRecyclerView.setAdapter(mDaftarPenyakitAdapter);
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
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
//                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);
        //tempChar keeps the first letter of the previous disease
        char tempChar = '0';
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {
            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            //Add a header to the list if it's a new letter
            if (obj.getString("nama_penyakit").charAt(0) != tempChar){
                mDaftarPenyakit.add(new Penyakit(obj.getString("nama_penyakit").substring(0, 1), true));
            }
            mDaftarPenyakit.add(new Penyakit(obj.getString("id_penyakit"),
                    obj.getString("nama_penyakit"),
                    obj.getString("image"),
                    obj.getString("ringkasan"),
                    obj.getString("penyebab"),
                    obj.getString("gejala"),
                    obj.getString("diagnosa"),
                    obj.getString("pencegahan"),
                    obj.getString("spesialis"),
                    false));

            tempChar = obj.getString("nama_penyakit").charAt(0);
        }
        mDaftarPenyakitAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPenyakitClick(int position) {
        Intent intent = new Intent(getActivity(), DetailPenyakitActivity.class);
        intent.putExtra("penyakit_terpilih", mDaftarPenyakit.get(position));
        startActivity(intent);
    }
}