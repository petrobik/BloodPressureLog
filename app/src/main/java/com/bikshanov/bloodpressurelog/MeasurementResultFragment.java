package com.bikshanov.bloodpressurelog;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.UUID;

public class MeasurementResultFragment extends Fragment {

    private static final String ARG_RESULT_ID = "result_id";
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
        mMeasurementResult = Results.get(getActivity()).getMeasurementResult(resultId);
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
        mDateButton.setText(Helpers.formatShortDate(mMeasurementResult.getDate()));
//        mDateButton.setEnabled(false);

        mTimeButton = (Button) v.findViewById(R.id.button_time);
        mTimeButton.setText(Helpers.formatTime(mMeasurementResult.getDate()));
//        mTimeButton.setEnabled(false);

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
}
