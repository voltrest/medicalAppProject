package com.example.medicalapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicalapp.R;
import com.example.medicalapp.ui.pandemi.KasusPerProvinsiActivity;
import com.example.medicalapp.ui.pandemi.PandemiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    //UI Elements
    private HomeViewModel homeViewModel;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    //Variables
    private static final String TAG = "Home Fragment";
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

        mButton = root.findViewById(R.id.button_pandemi);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PandemiActivity.class);
                startActivity(intent);
            }
        });

        getNewsData();
        return root;
    }

    private void getNewsData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://newsapi.org/v2/everything?domains=techcrunch.com,thenextweb.com&apiKey=c9db04fc36734c84bb6ea4c978db44a9\n";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                mProgressBar.setVisibility(View.GONE);
                try {
                    List<HashMap<String, String>> articleList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("ok")){
                        int totalResults = jsonObject.getInt("totalResults");
                        if (totalResults == 0){
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            int count = jsonArray.length();

                            for (int i = 0; i < count; i++){
                                try {
                                    HashMap<String, String> article = getArticleData((JSONObject) jsonArray.get(i));
                                    articleList.add(article);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //no results found
                        }
                    } else {
                        //something went wrong
                    }
//                    loadIntoRecyclerView(jsonArray);
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

    private HashMap<String, String> getArticleData(JSONObject articleJSON){
        HashMap<String, String> article = new HashMap<>();
        String title = "-News Title-";
        try {
            if (!articleJSON.isNull("title")){
                title = articleJSON.getString("title");
            }
            article.put("title", title);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return article;
    }

//    private void loadIntoRecyclerView(List<HashMap<String, String>>){
//        try{
//
//            }
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
//    }
}