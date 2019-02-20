package com.bikshanov.bloodpressurelog;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class PieChartFragment extends Fragment {

    private PieChart mPieChart;

    private final int NORMAL = 100;
    private final int ELEVATED = 200;
    private final int STAGE1 = 300;
    private final int STAGE2 = 400;
    private final int STAGE3 = 500;
    private final int OPTIMAL = 600;

    public PieChartFragment() {
        // Required empty public constructor
    }

    public static PieChartFragment newInstance() {
        PieChartFragment fragment = new PieChartFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_piechart, container, false);

        List<MeasurementResult> results = ResultsStore.get(getActivity()).getMeasurementResults();
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

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(optimal, 0));
        entries.add(new PieEntry(normal, 1));
        entries.add(new PieEntry(elevated, 2));
        entries.add(new PieEntry(stage1, 3));
        entries.add(new PieEntry(stage2, 4));
        entries.add(new PieEntry(stage3, 5));

        PieDataSet dataSet = new PieDataSet(entries, "");

        final int[] COLORS = {ContextCompat.getColor(getActivity(), R.color.green),
                ContextCompat.getColor(getActivity(), R.color.light_green),
                ContextCompat.getColor(getActivity(), R.color.yellow),
                ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.red),
                ContextCompat.getColor(getActivity(), R.color.deep_red)};
        dataSet.setColors(COLORS);

        List<String> labels = new ArrayList<>();
        labels.add("Optimal");
        labels.add("Normal");
        labels.add("Elevated");
        labels.add("Stage 1");
        labels.add("Stage 2");
        labels.add("Stage 3");

        PieData pieData = new PieData(dataSet);

        pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));

        mPieChart.setData(pieData);

        return view;
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
