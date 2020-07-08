package com.example.medicalapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.medicalapp.R;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Integer> color;
    private int[] mImageIds = new int[] {R.drawable.ambiguous_genitalia, R.drawable.ambiguous_genitalia, R.drawable.ambiguous_genitalia};
    private List<String> mArticleTitles;
    private String[] mStrings = new String[] {"Halo guys", "Nama Saya", "Adalah Winston"};

    //ImageAdapter(context, list<> images)

    ImageAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
//        return color.size();
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.item_slider, null);
//
//        TextView textView = (TextView) view.findViewById(R.id.textView);
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
//
//        textView.setText(colorName.get(position));
//        linearLayout.setBackgroundColor(color.get(position));
//
//        ViewPager viewPager = (ViewPager) container;
//        viewPager.addView(view, 0);
//
//        return view;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_slider_item, null);

        TextView textView = view.findViewById(R.id.image_slider_title);
        ImageView imageView = view.findViewById(R.id.image_slider_image);

        textView.setText(mStrings[position]);
        imageView.setImageResource(mImageIds[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;

//        ImageView imageView = new ImageView(mContext);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(mImageIds[position]);
//
//        container.addView(imageView, 0);
//        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

//        container.removeView((ImageView) object);
    }
}
