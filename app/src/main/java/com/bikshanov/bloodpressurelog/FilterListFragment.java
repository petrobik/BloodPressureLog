package com.bikshanov.bloodpressurelog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FilterListFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] items = {"Last 7 days", "Last 30 days", "All time"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select...")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Log.d("FilterDialogFragment", "1");
                                break;
                            case 1:
                                Log.d("FilterDialogFragment", "2");
                                break;
                            case 2:
                                Log.d("FilterDialogFragment", "3");
                                break;
                        }
                    }
                });

        return builder.create();
    }
}
