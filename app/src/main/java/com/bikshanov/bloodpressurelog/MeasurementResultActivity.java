package com.bikshanov.bloodpressurelog;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class MeasurementResultActivity extends SingleFragmentActivity {

    public static final String EXTRA_RESULT_ID = "com.bikshanov.bloodpressurelog.result_id";

    @Override
    protected Fragment createFragment() {
        UUID result_id = (UUID) getIntent().getSerializableExtra(EXTRA_RESULT_ID);
        return MeasurementResultFragment.newInstance(result_id);
    }

    public static Intent newIntent(Context packageContext, UUID resultId) {
        Intent intent = new Intent(packageContext, MeasurementResultActivity.class);
        intent.putExtra(EXTRA_RESULT_ID, resultId);
        return intent;
    }
}
