package com.example.medicalapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.medicalapp.ui.daftarPenyakit.DaftarPenyakitFragment;
import com.example.medicalapp.ui.home.HomeFragment;
import com.example.medicalapp.ui.rumahSakit.RumahSakitFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    //UI Elements
    SpaceNavigationView mNavigationView;
    Fragment mHomeFragment;
    Fragment mDaftarPenyakitFragment;
    Fragment mRumahSakitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//////////
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
///////
        initFragments();
        setFragment(mHomeFragment);
        mNavigationView = findViewById(R.id.space);

        mNavigationView.initWithSaveInstanceState(savedInstanceState);
        mNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_library_books_black_24dp));
        mNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_face_black_24dp));
        mNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home_black_24dp));
        mNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_more_vert_black_24dp));

        mNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                mNavigationView.setCentreButtonSelectable(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
                switch (itemIndex){
                    case 0:
                        setFragment(mHomeFragment);
                        break;
                    case 1:
                        setFragment(mDaftarPenyakitFragment);
                        break;
                    case 2:
                        setFragment(mRumahSakitFragment);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFragments(){
        mHomeFragment = new HomeFragment();
        mDaftarPenyakitFragment = new DaftarPenyakitFragment();
        mRumahSakitFragment = new RumahSakitFragment();
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    private void setFragment1(final Fragment fragment){
//        Handler mHandler = new Handler();
//
//        Runnable mPendingRunnable = new Runnable() {
//            @Override
//            public void run() {
//                // update the main content by replacing fragments
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                        android.R.anim.fade_out);
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        };
//
//        // If mPendingRunnable is not null, then add to the message queue
//        if (mPendingRunnable != null) {
//            mHandler.post(mPendingRunnable);
//        }
//    }
}
