package com.bikshanov.bloodpressurelog;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    private TextView mSysMaxTextView;
    private TextView mSysMinTextView;
    private TextView mSysAverageTextView;
    private TextView mDiaMaxTextView;
    private TextView mDiaMinTextView;
    private TextView mDiaAverageTextView;
    private TextView mPulseMaxTextView;
    private TextView mPulseMinTextView;
    private TextView mPulseAverageTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        mSysMinTextView = view.findViewById(R.id.min_sys_value);
        mSysMaxTextView = view.findViewById(R.id.max_sys_value);
        mSysAverageTextView = view.findViewById(R.id.average_sys_value);
        mDiaMinTextView = view.findViewById(R.id.min_dia_value);
        mDiaMaxTextView = view.findViewById(R.id.max_dia_value);
        mDiaAverageTextView = view.findViewById(R.id.average_dia_value);
        mPulseMaxTextView = view.findViewById(R.id.max_pulse_value);
        mPulseMinTextView = view.findViewById(R.id.min_pulse_value);
        mPulseAverageTextView = view.findViewById(R.id.average_pulse_value);

        List<MeasurementResult> results = ResultsStore.get(getActivity()).getMeasurementResults();

        if (results.size() > 0) {
            mSysMaxTextView.setText(Integer.toString(getSysMaxValue(results)));
            mSysMinTextView.setText(Integer.toString(getSysMinValue(results)));
            mSysAverageTextView.setText(Integer.toString(getSysAverage(results)));
            mDiaMaxTextView.setText(Integer.toString(getDiaMaxValue(results)));
            mDiaMinTextView.setText(Integer.toString(getDiaMinValue(results)));
            mDiaAverageTextView.setText(Integer.toString(getDiaAverage(results)));
            mPulseMaxTextView.setText(Integer.toString(getPulseMaxValue(results)));
            mPulseMinTextView.setText(Integer.toString(getPulseMinValue(results)));
            mPulseAverageTextView.setText(Integer.toString(getPulseAverage(results)));
        }

        return view;
    }

    public int getSysMaxValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.max(results, new SysValuesComparator());

        return result.getSysBloodPressure();
    }

    public int getSysMinValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.min(results, new SysValuesComparator());

        return result.getSysBloodPressure();
    }

    public int getDiaMaxValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.max(results, new DiaValuesComparator());

        return result.getDiaBloodPressure();
    }

    public int getDiaMinValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.min(results, new DiaValuesComparator());

        return result.getDiaBloodPressure();
    }

    public int getSysAverage(List<MeasurementResult> results) {
        int sum = 0;
        for (MeasurementResult result: results) {
            sum += result.getSysBloodPressure();
        }

        return sum / results.size();
    }

    public int getDiaAverage(List<MeasurementResult> results) {
        int sum = 0;
        for (MeasurementResult result: results) {
            sum += result.getDiaBloodPressure();
        }

        return sum / results.size();
    }

    public int getPulseMinValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.min(results, new PulseValuesComparator());

        return result.getPulse();
    }

    public int getPulseMaxValue(List<MeasurementResult> results) {

        MeasurementResult result = Collections.max(results, new PulseValuesComparator());

        return result.getPulse();
    }

    public int getPulseAverage(List<MeasurementResult> results) {
        int sum = 0;
        for (MeasurementResult result: results) {
            sum += result.getPulse();
        }

        return sum / results.size();
    }

}

