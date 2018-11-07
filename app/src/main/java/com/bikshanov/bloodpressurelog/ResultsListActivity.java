package com.bikshanov.bloodpressurelog;

import android.support.v4.app.Fragment;

public class ResultsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ResultsListFragment();
    }
}
