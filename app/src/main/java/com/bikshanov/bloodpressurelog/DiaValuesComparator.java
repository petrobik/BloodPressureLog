package com.bikshanov.bloodpressurelog;

import java.util.Comparator;

public class DiaValuesComparator implements Comparator<MeasurementResult> {
    @Override
    public int compare(MeasurementResult result1, MeasurementResult result2) {
        if (result1.getDiaBloodPressure() < result2.getDiaBloodPressure()) {
            return -1;
        }
        if (result1.getDiaBloodPressure() > result2.getDiaBloodPressure()) {
            return 1;
        }
        return 0;
    }
}
