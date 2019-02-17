package com.bikshanov.bloodpressurelog;

import java.util.Comparator;

public class PulseValuesComparator implements Comparator<MeasurementResult> {
    @Override
    public int compare(MeasurementResult result1, MeasurementResult result2) {
        if (result1.getPulse() < result2.getPulse()) {
            return -1;
        }
        if (result1.getPulse() > result2.getPulse()) {
            return 1;
        }
        return 0;
    }
}
