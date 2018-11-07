package com.bikshanov.bloodpressurelog;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Results {

    private static Results sResults;
    private List<MeasurementResult> mMeasurementResults;

    public static Results get(Context context) {
        if (sResults == null) {
            sResults = new Results(context);
        }

        return sResults;
    }

    private Results(Context context) {

        mMeasurementResults = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            MeasurementResult result = new MeasurementResult();
            result.setSysBloodPressure(120 + i);
            result.setDiaBloodPressure(80 + i);
            result.setPulse(60 + i);
            result.setHealth("Normal");
            result.setComment("");
            result.setArrhythmia(i % 2 == 0);
            mMeasurementResults.add(result);
        }
    }

    public List<MeasurementResult> getMeasurementResults() {
        return mMeasurementResults;
    }

    public MeasurementResult getMeasurementResult(UUID id) {
        for (MeasurementResult result: mMeasurementResults) {
            if (result.getId().equals(id)) {
                return result;
            }
        }

        return null;
    }
}
