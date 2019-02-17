package com.bikshanov.bloodpressurelog;

import java.util.Comparator;

public class SysValuesComparator implements Comparator<MeasurementResult> {
    @Override
    public int compare(MeasurementResult result1, MeasurementResult result2) {
        if (result1.getSysBloodPressure() < result2.getSysBloodPressure()) {
            return -1;
        }
        if (result1.getSysBloodPressure() > result2.getSysBloodPressure()) {
            return 1;
        }
        return 0;
    }
}
