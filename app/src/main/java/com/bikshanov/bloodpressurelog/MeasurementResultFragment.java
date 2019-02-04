package com.bikshanov.bloodpressurelog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.Date;
import java.util.UUID;

public class MeasurementResultFragment extends Fragment {

    private static final String ARG_RESULT_ID = "result_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private MeasurementResult mMeasurementResult;
    private TextInputEditText mSysPressureEditText;
    private TextInputEditText mDiaPressureEditText;
    private TextInputEditText mPulseEditText;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mArrhythmiaCheckBox;

    public static MeasurementResultFragment newInstance(UUID result_id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESULT_ID, result_id);
        MeasurementResultFragment fragment = new MeasurementResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMeasurementResult = new MeasurementResult();

        UUID resultId = (UUID) getArguments().getSerializable(ARG_RESULT_ID);
        mMeasurementResult = ResultsStore.get(getActivity()).getMeasurementResult(resultId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_result, container, false);

        String sysPressure = Integer.toString(mMeasurementResult.getSysBloodPressure());
        String diaPressure = Integer.toString(mMeasurementResult.getDiaBloodPressure());
        String pulse = Integer.toString(mMeasurementResult.getPulse());

        mSysPressureEditText = (TextInputEditText) v.findViewById(R.id.sys_pressure_value);
        mSysPressureEditText.setText(sysPressure);
        mSysPressureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mMeasurementResult.setSysBloodPressure(Integer.parseInt(charSequence.toString()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDiaPressureEditText = (TextInputEditText) v.findViewById(R.id.dia_pressure_value);
        mDiaPressureEditText.setText(diaPressure);
        mDiaPressureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                mMeasurementResult.setDiaBloodPressure(Integer.parseInt(charSequence.toString()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPulseEditText = (TextInputEditText) v.findViewById(R.id.pulse_value);
        mPulseEditText.setText(pulse);
        mPulseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                mMeasurementResult.setPulse(Integer.parseInt(charSequence.toString()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.button_date);
        updateDate();
//        mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mMeasurementResult.getDate());
                dialog.setTargetFragment(MeasurementResultFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.button_time);
        updateTime();
//        mTimeButton.setEnabled(false);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
//                TimePickerFragment dialog = new TimePickerFragment();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mMeasurementResult.getDate());
                dialog.setTargetFragment(MeasurementResultFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mArrhythmiaCheckBox = v.findViewById(R.id.checkBox_arrhythmia);
        mArrhythmiaCheckBox.setChecked(mMeasurementResult.isArrhythmia());
        mArrhythmiaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mMeasurementResult.setArrhythmia(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mMeasurementResult.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mMeasurementResult.setDate(time);
            updateTime();

        }


    }

    private void updateTime() {
        mTimeButton.setText(Helpers.formatTime(mMeasurementResult.getDate()));
    }

    private void updateDate() {
        mDateButton.setText(Helpers.formatShortDate(mMeasurementResult.getDate()));
    }
}
