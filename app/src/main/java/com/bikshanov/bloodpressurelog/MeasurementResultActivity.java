package com.bikshanov.bloodpressurelog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.UUID;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
        confirmDialog.setMessage(R.string.dialog_message);
        confirmDialog.setPositiveButton(R.string.dialog_discard, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        confirmDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        confirmDialog.show();
    }
}