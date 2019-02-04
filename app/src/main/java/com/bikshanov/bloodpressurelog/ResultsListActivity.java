package com.bikshanov.bloodpressurelog;

import androidx.fragment.app.Fragment;

public class ResultsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ResultsListFragment();
    }
}
