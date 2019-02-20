package com.bikshanov.bloodpressurelog;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartsPagerFragment extends Fragment {

    private ViewPager mViewPager;

    private static final int NUM_PAGES = 2;


    public ChartsPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_pager, container, false);

        mViewPager = view.findViewById(R.id.chart_pager);

        FragmentManager fragmentManager = getFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return ChartFragment.newInstance();
                } else {
                    return PieChartFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return NUM_PAGES;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "Chart";
                } else {
                    return "PieChart";
                }
            }
        });

        return view;
    }

}
