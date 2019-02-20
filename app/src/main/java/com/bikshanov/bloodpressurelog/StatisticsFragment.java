package com.bikshanov.bloodpressurelog;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

    private PieChart mPieChart;

    private final int NORMAL = 100;
    private final int ELEVATED = 200;
    private final int STAGE1 = 300;
    private final int STAGE2 = 400;
    private final int STAGE3 = 500;
    private final int OPTIMAL = 600;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        getActivity().setTitle(getResources().getString(R.string.fragment_statistics_title));

        mSysMinTextView = view.findViewById(R.id.min_sys_value);
        mSysMaxTextView = view.findViewById(R.id.max_sys_value);
        mSysAverageTextView = view.findViewById(R.id.average_sys_value);
        mDiaMinTextView = view.findViewById(R.id.min_dia_value);
        mDiaMaxTextView = view.findViewById(R.id.max_dia_value);
        mDiaAverageTextView = view.findViewById(R.id.average_dia_value);
        mPulseMaxTextView = view.findViewById(R.id.max_pulse_value);
        mPulseMinTextView = view.findViewById(R.id.min_pulse_value);
        mPulseAverageTextView = view.findViewById(R.id.average_pulse_value);

        mPieChart = view.findViewById(R.id.pieChart);

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

            int optimal = 0;
            int normal = 0;
            int elevated = 0;
            int stage1 = 0;
            int stage2 = 0;
            int stage3 = 0;
            int measSum = results.size();

            for (MeasurementResult result: results) {
                switch (Helpers.getPressureCategory(result.getSysBloodPressure(),
                        result.getDiaBloodPressure())) {
                    case OPTIMAL:
                        optimal++;
                        break;
                    case NORMAL:
                        normal++;
                        break;
                    case ELEVATED:
                        elevated++;
                        break;
                    case STAGE1:
                        stage1++;
                        break;
                    case STAGE2:
                        stage2++;
                        break;
                    case STAGE3:
                        stage3++;
                        break;
                    default:
                        normal++;
                        break;
                }
            }

            mPieChart = view.findViewById(R.id.pieChart);
            mPieChart.setUsePercentValues(true);
            mPieChart.getDescription().setEnabled(false);
            mPieChart.setDrawEntryLabels(false);
//            mPieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
//            mPieChart.setDrawHoleEnabled(false);
            mPieChart.setHoleRadius(30f);
            mPieChart.setDrawSlicesUnderHole(false);
            mPieChart.setTransparentCircleRadius(40f);

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(optimal, getResources().getString(R.string.optimal)));
            entries.add(new PieEntry(normal, getResources().getString(R.string.normal)));
            entries.add(new PieEntry(elevated, getResources().getString(R.string.elevated)));
            entries.add(new PieEntry(stage1, getResources().getString(R.string.stage1)));
            entries.add(new PieEntry(stage2, getResources().getString(R.string.stage2)));
            entries.add(new PieEntry(stage3, getResources().getString(R.string.stage3)));

            Legend legend = mPieChart.getLegend();
            legend.setWordWrapEnabled(true);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//            legend.setTextSize(14f);

            PieDataSet dataSet = new PieDataSet(entries, "");

            final int[] COLORS = {ContextCompat.getColor(getActivity(), R.color.green),
                    ContextCompat.getColor(getActivity(), R.color.light_green),
                    ContextCompat.getColor(getActivity(), R.color.yellow),
                    ContextCompat.getColor(getActivity(), R.color.orange),
                    ContextCompat.getColor(getActivity(), R.color.red),
                    ContextCompat.getColor(getActivity(), R.color.deep_red)};
            dataSet.setColors(COLORS);

//            List<String> labels = new ArrayList<>();
//            labels.add("Optimal");
//            labels.add("Normal");
//            labels.add("Elevated");
//            labels.add("Stage 1");
//            labels.add("Stage 2");
//            labels.add("Stage 3");

            PieData pieData = new PieData(dataSet);

            pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
            pieData.setValueTextSize(15f);
            pieData.setValueTextColor(getResources().getColor(R.color.colorPrimaryText));

            mPieChart.setData(pieData);
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

    private class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            mFormat = format;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value == 0) {
                return "";
            }
            return mFormat.format(value) + "%";
        }
    }
}

