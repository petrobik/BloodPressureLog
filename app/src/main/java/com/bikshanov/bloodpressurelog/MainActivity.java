package com.bikshanov.bloodpressurelog;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.bikshanov.bloodpressurelog.database.ResultsDbSchema;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private static boolean isFab;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    isFab = true;
                    loadFragment(new ResultsListFragment());
                    return true;
                case R.id.navigation_charts:
                    isFab = false;
                    loadFragment(new ChartFragment());
                    return true;
                case R.id.navigation_statistics:
                    isFab = false;
                    loadFragment(new StatisticsFragment());
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.floatingActionButton);
        if (!isFab) {
            mFab.hide();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            loadFragment(new ResultsListFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (fragment instanceof ResultsListFragment) {
            mFab.show();
        } else {
            mFab.hide();
        }
//            if (!mFab.isShown()) {
//                mFab.show();
//            }
//
//            if (mFab.isShown()) {
//                mFab.hide();
//            }

//        transaction.addToBackStack(null);
        transaction.commit();
    }
}