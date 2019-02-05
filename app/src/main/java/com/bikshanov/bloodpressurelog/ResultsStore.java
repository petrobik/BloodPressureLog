package com.bikshanov.bloodpressurelog;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResultsStore {

    private static ResultsStore sResultsStore;
    private List<MeasurementResult> mMeasurementResults;
//    private Map<UUID, MeasurementResult> mMeasurementResults;

    public static ResultsStore get(Context context) {
        if (sResultsStore == null) {
            sResultsStore = new ResultsStore(context);
        }

        return sResultsStore;
    }

    private ResultsStore(Context context) {

        mMeasurementResults = new ArrayList<>();

//        mMeasurementResults = new LinkedHashMap<>();
//        for (int i = 0; i < 100; i++) {
//            MeasurementResult result = new MeasurementResult();
//            result.setSysBloodPressure(110 + i);
//            result.setDiaBloodPressure(70 + i);
//            result.setPulse(60 + i);
//            result.setHealth("Normal");
//            result.setComment("Comment " + i);
//            result.setArrhythmia(i % 2 == 0);
//            mMeasurementResults.add(result);
////            mMeasurementResults.put(result.getId(), result);
//        }
    }

    public List<MeasurementResult> getMeasurementResults() {
        return mMeasurementResults;
//        return new ArrayList<>(mMeasurementResults.values());
    }

    public MeasurementResult getMeasurementResult(UUID id) {
        for (MeasurementResult result: mMeasurementResults) {
            if (result.getId().equals(id)) {
                return result;
            }
        }

        return null;
//        return mMeasurementResults.get(id);
    }

    public void addResult(MeasurementResult result) {
        mMeasurementResults.add(result);
//        mMeasurementResults.put(result.getId(), result);
    }

    public void removeResult(MeasurementResult result) {
        mMeasurementResults.remove(result.getId());
//        mMeasurementResults.remove(result.getId());
    }
}
