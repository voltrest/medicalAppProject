package com.example.medicalapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.medicalapp.R;
import com.example.medicalapp.adapters.ArticlesRecyclerAdapter;
import com.example.medicalapp.models.Article;
import com.example.medicalapp.ui.illnessList.illnessDetails.IllnessDetailActivity;
import com.example.medicalapp.ui.pandemic.PandemicActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements ArticlesRecyclerAdapter.OnArticleListener{
    //UI Elements
    private LinearLayout mLinearLayout1;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private ViewPager mViewPager;
    private TabLayout mIndicator;

    //Variables
    private static final String TAG = "Home Fragment";
    private Activity mActivity;
    ListView listView;
    private ArrayList<Article> mArticleList = new ArrayList<>();
    private ArticlesRecyclerAdapter mArticlesRecyclerAdapter;

    private int[] mImageIds = new int[] {R.drawable.ambiguous_genitalia, R.drawable.ambiguous_genitalia, R.drawable.ambiguous_genitalia};

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
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mLinearLayout1 = root.findViewById(R.id.button_pandemic);
        mLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PandemicActivity.class);
                startActivity(intent);
            }
        });

        mViewPager = root.findViewById(R.id.image_container);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        mViewPager.setAdapter(imageAdapter);

        mIndicator = root.findViewById(R.id.indicator);
        mIndicator.setupWithViewPager(mViewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        mRecyclerView = root.findViewById(R.id.recycler_articles);
        initRecyclerView();

        mProgressBar = root.findViewById(R.id.progress_bar_article);

        getData("http://192.168.1.25/medicalapp/mobileapp/get_article.php");

        return root;
    }

    private void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArticlesRecyclerAdapter = new ArticlesRecyclerAdapter(mArticleList, this);
        mRecyclerView.setAdapter(mArticlesRecyclerAdapter);
    }

    @Override
    public void onArticleClick(int position) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("chosen article", mArticleList.get(position));
        startActivity(intent);
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

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoRecyclerView(String json) throws JSONException {
        mArticleList.clear();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date tanggalTulis = new Date();
            Date tanggalCopy = new Date();
            try {
                tanggalTulis = format.parse(obj.getString("tanggal_tulis"));
                tanggalCopy = format.parse(obj.getString("tanggal_copy"));
            } catch (ParseException e){
                e.printStackTrace();
            }
            mArticleList.add(new Article(obj.getString("id_artikel"),
                    obj.getString("judul"),
                    obj.getString("gambar_artikel"),
                    obj.getString("badan"),
                    obj.getString("penulis"),
                    tanggalTulis,
                    tanggalCopy));

            Log.d(TAG, "judul: " + obj.getString("judul"));
            Log.d(TAG, "tanggal tulis: " + tanggalTulis);
        }
        mArticlesRecyclerAdapter.notifyDataSetChanged();
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mViewPager.getCurrentItem() < mImageIds.length - 1){
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        } else {
                            mViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}