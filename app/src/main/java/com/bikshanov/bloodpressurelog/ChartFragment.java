package com.bikshanov.bloodpressurelog;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChartFragment extends Fragment {

    public static final String TITLE = "Charts";

    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        getActivity().setTitle(getResources().getString(R.string.fragment_charts_title));

        LineChart lineChart = view.findViewById(R.id.chart);

        lineChart.setTouchEnabled(true);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setExtraBottomOffset(20);
        lineChart.setNoDataText(getResources().getString(R.string.chart_empty));
        lineChart.setNoDataTextColor(getResources().getColor(R.color.colorPrimaryText));

        LimitLine sysLimit = new LimitLine(120);
        LimitLine diaLimit = new LimitLine(80);

        sysLimit.setLineColor(getResources().getColor(R.color.green));
        diaLimit.setLineColor(getResources().getColor(R.color.green));

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxis.setAxisMinimum(20);

        yAxis.addLimitLine(sysLimit);
        yAxis.addLimitLine(diaLimit);

        yAxis.setDrawLimitLinesBehindData(true);

        List<Entry> sysEntries = new ArrayList<>();
        List<Entry> diaEntries = new ArrayList<>();
        List<Entry> pulseEntries = new ArrayList<>();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        List<MeasurementResult> results = ResultsStore.get(getActivity()).getMeasurementResults();

//         Sort list of measurements by date before setting the adapter
        if (results.size() > 0) {
            Collections.sort(results, new Comparator<MeasurementResult>() {
                @Override
                public int compare(MeasurementResult result1, MeasurementResult result2) {
                    return result1.getDate().compareTo(result2.getDate());
                }
            });
        }

        String[] dateValues = new String[results.size()];

        for (int i = 0; i < results.size(); i++) {
            sysEntries.add(new Entry((float) i, (float) results.get(i).getSysBloodPressure()));
            diaEntries.add(new Entry((float) i, (float) results.get(i).getDiaBloodPressure()));
            pulseEntries.add(new Entry((float) i, (float) results.get(i).getPulse()));
            dateValues[i] = Helpers.formatShortDate(getActivity(), results.get(i).getDate());
        }

        xAxis.setGranularity(1f);

        if (isPortrait()) {
            yAxis.setLabelCount(10);
            xAxis.setLabelRotationAngle(-90);
        }

        xAxis.setValueFormatter(new DateXAxisValueFormatter(dateValues));

//        for (MeasurementResult result: ResultsStore.get(getActivity()).getMeasurementResults()) {
//            int i = getId();
//            sysEntries.add(new Entry((float) i, (float) result.getSysBloodPressure()));
//        }

        LineDataSet sysDataSet = new LineDataSet(sysEntries, getResources().getString(R.string.sys_label));
        LineDataSet diaDataSet = new LineDataSet(diaEntries, getResources().getString(R.string.dia_label));
        LineDataSet pulseDataSet = new LineDataSet(pulseEntries, getResources().getString(R.string.pulse_label));

//        sysDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        int sysColor = Color.RED;
        sysDataSet.setColor(sysColor);
        sysDataSet.setLineWidth(3);
        sysDataSet.setCircleColor(sysColor);
        sysDataSet.setCircleHoleColor(sysColor);
        sysDataSet.setDrawHighlightIndicators(false);
        sysDataSet.setDrawValues(false);

        int diaColor = Color.BLUE;
        diaDataSet.setColor(diaColor);
        diaDataSet.setLineWidth(3);
        diaDataSet.setCircleColor(diaColor);
        diaDataSet.setCircleHoleColor(diaColor);
        diaDataSet.setDrawHighlightIndicators(false);
        diaDataSet.setDrawValues(false);

        int pulseColor = Color.MAGENTA;
        pulseDataSet.setColor(pulseColor);
        pulseDataSet.setLineWidth(3);
        pulseDataSet.setCircleColor(pulseColor);
        pulseDataSet.setCircleHoleColor(pulseColor);
        pulseDataSet.setDrawHighlightIndicators(false);
        pulseDataSet.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(sysDataSet);
        lineData.addDataSet(diaDataSet);
        lineData.addDataSet(pulseDataSet);

        if (sysEntries.size() >= 5) {
            lineChart.setData(lineData);
        }

        lineChart.invalidate();

        return view;

    }

    public boolean isPortrait() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }
        return false;
    }

    private class DateXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public DateXAxisValueFormatter(String[] values) {
            mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
}
