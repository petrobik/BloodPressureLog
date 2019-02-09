package com.bikshanov.bloodpressurelog.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bikshanov.bloodpressurelog.MeasurementResult;
import com.bikshanov.bloodpressurelog.database.ResultsDbSchema.ResultsTable;

import java.util.Date;
import java.util.UUID;


public class ResultCursorWrapper extends CursorWrapper {

    public ResultCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MeasurementResult getMeasurementResult() {
        String uuidString = getString(getColumnIndex(ResultsTable.Cols.UUID));
        int sysPressure = getInt(getColumnIndex(ResultsTable.Cols.SYS));
        int diaPressure = getInt(getColumnIndex(ResultsTable.Cols.DIA));
        int pulse = getInt(getColumnIndex(ResultsTable.Cols.PULSE));
        int isArrhythmia = getInt(getColumnIndex(ResultsTable.Cols.ARRHYTHMIA));
        String arm = getString(getColumnIndex(ResultsTable.Cols.ARM));
        String comment = getString(getColumnIndex(ResultsTable.Cols.COMMENT));
        long date = getLong(getColumnIndex(ResultsTable.Cols.DATE));

        MeasurementResult measurementResult = new MeasurementResult(UUID.fromString(uuidString));
        measurementResult.setSysBloodPressure(sysPressure);
        measurementResult.setDiaBloodPressure(diaPressure);
        measurementResult.setPulse(pulse);
        measurementResult.setArrhythmia(isArrhythmia != 0);
        measurementResult.setArm(arm);
        measurementResult.setComment(comment);
        measurementResult.setDate(new Date(date));

        return measurementResult;
    }
}
