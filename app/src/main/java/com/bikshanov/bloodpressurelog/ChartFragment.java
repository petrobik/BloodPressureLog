package com.bikshanov.bloodpressurelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChartFragment extends Fragment {

    public static final String TITLE = "Charts";
    public static final int WEEK = 10;
    public static final int MONTH = 20;
    public static final int ALLTIME = 30;

    private int list;

    private List<MeasurementResult> mResults;

    private LineChart mLineChart;


    public static ChartFragment newInstance() {
        ChartFragment fragment = new ChartFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        getActivity().setTitle(getResources().getString(R.string.fragment_charts_title));

        list = ALLTIME;

        mLineChart = view.findViewById(R.id.chart);

        mLineChart.setTouchEnabled(true);
        mLineChart.setAutoScaleMinMaxEnabled(true);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setPinchZoom(false);
        mLineChart.setScaleYEnabled(false);
        mLineChart.setExtraBottomOffset(20);
        mLineChart.setNoDataText(getResources().getString(R.string.chart_empty));
        mLineChart.setNoDataTextColor(getResources().getColor(R.color.colorPrimaryText));

        LimitLine sysLimit = new LimitLine(120);
        LimitLine diaLimit = new LimitLine(80);

        sysLimit.setLineColor(getResources().getColor(R.color.green));
        diaLimit.setLineColor(getResources().getColor(R.color.green));

        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxis = mLineChart.getAxisLeft();
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxis.setAxisMinimum(20);

        yAxis.addLimitLine(sysLimit);
        yAxis.addLimitLine(diaLimit);

        yAxis.setDrawLimitLinesBehindData(true);

        Legend legend = mLineChart.getLegend();
//        legend.setWordWrapEnabled(true);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(13f);
        legend.setTextColor(R.color.colorPrimaryText);

        List<Entry> sysEntries = new ArrayList<>();
        List<Entry> diaEntries = new ArrayList<>();
        List<Entry> pulseEntries = new ArrayList<>();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date beforeDate;

//        switch (list) {
//            case WEEK:
//                calendar.add(Calendar.DATE, -7);
//                beforeDate = calendar.getTime();
//                mResults = ResultsStore.get(getActivity()).getLastResults(beforeDate);
//                break;
//            case MONTH:
//                calendar.add(Calendar.DATE, -30);
//                beforeDate = calendar.getTime();
//                mResults = ResultsStore.get(getActivity()).getLastResults(beforeDate);
//                break;
//                default:
//                    mResults = ResultsStore.get(getActivity()).getMeasurementResults();
//
//        }

        setData(ALLTIME);

//        Sort list of measurements by date before setting the adapter
        if (mResults.size() > 0) {
            Collections.sort(mResults, new Comparator<MeasurementResult>() {
                @Override
                public int compare(MeasurementResult result1, MeasurementResult result2) {
                    return result1.getDate().compareTo(result2.getDate());
                }
            });
        }


//        String[] dateValues = new String[results.size()];
        String[] dateValues = new String[mResults.size()];

        for (int i = 0; i < mResults.size(); i++) {
            sysEntries.add(new Entry((float) i, (float) mResults.get(i).getSysBloodPressure()));
            diaEntries.add(new Entry((float) i, (float) mResults.get(i).getDiaBloodPressure()));
            pulseEntries.add(new Entry((float) i, (float) mResults.get(i).getPulse()));
            dateValues[i] = Helpers.formatShortDate(getActivity(), mResults.get(i).getDate());
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
            mLineChart.setData(lineData);
        }

        mLineChart.invalidate();

        return view;

    }

    private void setData(int list) {
        Calendar calendar = Calendar.getInstance();
        Date beforeDate;

        switch (list) {
            case WEEK:
                calendar.add(Calendar.DATE, -7);
                beforeDate = calendar.getTime();
                mResults = ResultsStore.get(getActivity()).getLastResults(beforeDate);
                break;
            case MONTH:
                calendar.add(Calendar.DATE, -30);
                beforeDate = calendar.getTime();
                mResults = ResultsStore.get(getActivity()).getLastResults(beforeDate);
                break;
            default:
                mResults = ResultsStore.get(getActivity()).getMeasurementResults();
        }

        mLineChart.invalidate();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_chart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final String[] items = {"Last 7 days", "Last 30 days", "All time"};

        AlertDialog.Builder filterDialog = new AlertDialog.Builder(getActivity());
        filterDialog.setTitle("Select...")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                setData(WEEK);
                                break;
                            case 1:
                                setData(MONTH);
                                break;
                            case 2:
                                setData(ALLTIME);
                                break;
                        }
                    }
                });

        filterDialog.show();

        return super.onOptionsItemSelected(item);
    }
}
