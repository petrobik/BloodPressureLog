package com.bikshanov.bloodpressurelog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChartFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        LineChart lineChart = view.findViewById(R.id.chart);

        lineChart.setTouchEnabled(true);
        lineChart.setAutoScaleMinMaxEnabled(true);

        LimitLine sysLimit = new LimitLine(130);
        LimitLine diaLimit = new LimitLine(80);

        sysLimit.setLineColor(getResources().getColor(R.color.green));
        diaLimit.setLineColor(getResources().getColor(R.color.green));


        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        yAxis.setAxisMinimum(20);
//        yAxis.setAxisMaximum(300);
        yAxis.setLabelCount(10);

        yAxis.addLimitLine(sysLimit);
        yAxis.addLimitLine(diaLimit);

        yAxis.setDrawLimitLinesBehindData(true);

        List<Entry> sysEntries = new ArrayList<>();
        List<Entry> diaEntries = new ArrayList<>();

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        List<MeasurementResult> results = ResultsStore.get(getActivity()).getMeasurementResults();

        for (int i = 0; i < results.size(); i++) {
            sysEntries.add(new Entry((float) i, (float) results.get(i).getSysBloodPressure()));
            diaEntries.add(new Entry((float) i, (float) results.get(i).getDiaBloodPressure()));
        }

//        for (MeasurementResult result: ResultsStore.get(getActivity()).getMeasurementResults()) {
//            int i = getId();
//            sysEntries.add(new Entry((float) i, (float) result.getSysBloodPressure()));
//        }

        LineDataSet sysDataSet = new LineDataSet(sysEntries, "SYS");
        LineDataSet diaDataSet = new LineDataSet(diaEntries, "DIA");

//        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

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

        LineData lineData = new LineData();
        lineData.addDataSet(sysDataSet);
        lineData.addDataSet(diaDataSet);

        lineChart.setData(lineData);
//        lineChart.setData(diaLineData);



        lineChart.invalidate();

        return view;

    }
}
