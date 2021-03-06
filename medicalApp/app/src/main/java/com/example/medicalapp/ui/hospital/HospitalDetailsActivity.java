package com.example.medicalapp.ui.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.medicalapp.R;
import com.example.medicalapp.map.HospitalDetailsParser;
import com.example.medicalapp.map.UrlReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;

public class HospitalDetailsActivity extends AppCompatActivity {

    //UI Elements
    private ImageView mPhotoImage;
    private RatingBar mRatingBar;
    private TextView mUserRatingsTotalText;
    private TextView mAddressText;
    private TextView mTelponText;
    private TextView mWebsiteText;

    //Variables
    private String mPlaceId;
    private HashMap<String, String> mHospital;
    private static final String TAG = "HospitalDetailsAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        mPhotoImage = findViewById(R.id.foto_rumah_sakit);
        mRatingBar = findViewById(R.id.rating_bar);
        mUserRatingsTotalText = findViewById(R.id.text_user_rating_total);
        mAddressText = findViewById(R.id.alamat_rumah_sakit);
        mTelponText = findViewById(R.id.telpon_rumah_sakit);
        mWebsiteText = findViewById(R.id.website_rumah_sakit);

        mPlaceId = getIntent().getStringExtra("place_id");
        getHospitalDetails();
    }

    private void setContent(){
        if (mHospital != null){
            String photoReference = mHospital.get("photo_reference");
            if (!photoReference.equals("-NA-")){
                setHospitalPhoto(photoReference);
            }

            if (!mHospital.get("rating").equals("-NA-")){
                mRatingBar.setRating(Float.valueOf(mHospital.get("rating")));
            }
            if (mHospital.get("user_ratings_total").equals("-NA-")){
                mUserRatingsTotalText.setText(mHospital.get("user_ratings_total"));
            }
            mAddressText.setText(mHospital.get("address"));
            mTelponText.setText(mHospital.get("phone_number"));
            mWebsiteText.setText(mHospital.get("website"));
        } else {
            Log.e(TAG, "setContent: mRumahSakit IS EMPTY AAAAAAAAAAAAAAAAAAAAA");
        }
    }

    private static class HospitalDetailsGetter extends AsyncTask<String, String, String> {
        private WeakReference<HospitalDetailsActivity> activityWeakReference;

        HospitalDetailsGetter(HospitalDetailsActivity activity){
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... urls) {
            String googlePlaceData = null;
            String url = urls[0];
            UrlReader urlReader = new UrlReader();

            try {
                googlePlaceData = urlReader.ReadURL(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return googlePlaceData;
        }

        @Override
        protected void onPostExecute(String s) {
            HospitalDetailsActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;

            HospitalDetailsParser dataParser = new HospitalDetailsParser();
            HashMap<String, String> tempHashMap;
            tempHashMap = dataParser.parse(s);

            if (!dataParser.isError()){
                activity.mHospital = tempHashMap;
                activity.setContent();
            }
        }
    }

    private void getHospitalDetails(){
        HospitalDetailsGetter hospitalDetailsGetter = new HospitalDetailsGetter(this);
        String url = getDetailUrl(mPlaceId);
        hospitalDetailsGetter.execute(url);
    }

    private void setHospitalPhoto(String photoReference){
        class SetHospitalPhoto extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... urls) {
                String url = urls[0];
                Bitmap googlePhotoData = null;

                try {
//                    InputStream inputStream = new URL(url).openStream();
//                    InputStream inputStream = (InputStream) new URL(url).getContent();
                    InputStream inputStream = (InputStream) new URL(url).getContent();
                    googlePhotoData = BitmapFactory.decodeStream(inputStream);

                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error retrieving/displaying image: " + e.getMessage());
                    e.printStackTrace();
                }

                return googlePhotoData;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                mPhotoImage.setImageBitmap(result);
            }
        }

        SetHospitalPhoto setHospitalPhoto = new SetHospitalPhoto();
        String url = getPhotoUrl(photoReference);
        setHospitalPhoto.execute(url);
    }

    private String getDetailUrl(String placeId){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googleURL.append("place_id=").append(placeId);
        googleURL.append("&language=").append("id");
        googleURL.append("&key=").append(getString(R.string.google_places_key));

        Log.d(TAG, "url = " + googleURL.toString());

        return googleURL.toString();
    }

    private String getPhotoUrl(String photoReference){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?");
        googleURL.append("&maxwidth=").append(500);
        googleURL.append("&photoreference=").append(photoReference);
        googleURL.append("&key=").append(getString(R.string.google_places_key));

        Log.d(TAG, "url = " + googleURL.toString());

        return googleURL.toString();
    }
}
