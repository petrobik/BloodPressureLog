package com.bikshanov.bloodpressurelog;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

        Random random = new Random();

//        mMeasurementResults = new LinkedHashMap<>();
        for (int i = 0; i < 20; i++) {
            MeasurementResult result = new MeasurementResult();
            result.setSysBloodPressure(random.nextInt(100 + 1) + 100);
            result.setDiaBloodPressure(random.nextInt(50 + 1) + 50);
            result.setPulse(60 + i);
            result.setHealth("Normal");
            result.setComment("Comment " + i);
            result.setArrhythmia(i % 2 == 0);
            result.setArm("right");
            mMeasurementResults.add(result);
//            mMeasurementResults.put(result.getId(), result);
        }
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
//        MeasurementResult result = getMeasurementResult(resultId)
        mMeasurementResults.remove(result);
    }
}
