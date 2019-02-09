package com.bikshanov.bloodpressurelog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.bikshanov.bloodpressurelog.database.ResultCursorWrapper;
import com.bikshanov.bloodpressurelog.database.ResultsBaseHelper;
import com.bikshanov.bloodpressurelog.database.ResultsDbSchema;
import com.bikshanov.bloodpressurelog.database.ResultsDbSchema.ResultsTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ResultsStore {

    private static ResultsStore sResultsStore;
//    private List<MeasurementResult> mMeasurementResults;
    private Context mContext;
    private SQLiteDatabase mDatabase;
//    private Map<UUID, MeasurementResult> mMeasurementResults;

    public static ResultsStore get(Context context) {
        if (sResultsStore == null) {
            sResultsStore = new ResultsStore(context);
        }

        return sResultsStore;
    }

    private ResultsStore(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new ResultsBaseHelper(mContext).getWritableDatabase();

//        mMeasurementResults = new ArrayList<>();

        Random random = new Random();

//        mMeasurementResults = new LinkedHashMap<>();
//        for (int i = 0; i < 20; i++) {
//            MeasurementResult result = new MeasurementResult();
//            result.setSysBloodPressure(random.nextInt(100 + 1) + 100);
//            result.setDiaBloodPressure(random.nextInt(50 + 1) + 50);
//            result.setPulse(60 + i);
//            result.setHealth("Normal");
//            result.setComment("Comment " + i);
//            result.setArrhythmia(i % 2 == 0);
//            result.setArm("right");
//            mMeasurementResults.add(result);
//            mMeasurementResults.put(result.getId(), result);
//        }
    }

    public List<MeasurementResult> getMeasurementResults() {
//        return mMeasurementResults;
//        return new ArrayList<>(mMeasurementResults.values());
//        return new ArrayList<>();
        List<MeasurementResult> measurementResults = new ArrayList<>();

        ResultCursorWrapper cursor = queryResults(null, null, ResultsTable.Cols.DATE + " DESC");

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                measurementResults.add(cursor.getMeasurementResult());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return measurementResults;
    }

    public MeasurementResult getMeasurementResult(UUID id) {
//        for (MeasurementResult result: mMeasurementResults) {
//            if (result.getId().equals(id)) {
//                return result;
//            }
//        }

        ResultCursorWrapper cursor = queryResults(
                ResultsTable.Cols.UUID + " = ?",
                new String[] { id.toString() },
                null
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getMeasurementResult();
        } finally {
            cursor.close();
        }

//        return mMeasurementResults.get(id);
    }

    public void updateResult(MeasurementResult result) {
        String uuidString = result.getId().toString();
        ContentValues values = getContentValues(result);

        mDatabase.update(ResultsTable.NAME, values, ResultsTable.Cols.UUID +
                " = ?", new String[] { uuidString });
    }

    private ResultCursorWrapper queryResults(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                ResultsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );

        return new ResultCursorWrapper(cursor);
    }


    public void addResult(MeasurementResult result) {
//        mMeasurementResults.add(result);
//        mMeasurementResults.put(result.getId(), result);
        ContentValues values = getContentValues(result);

        mDatabase.insert(ResultsTable.NAME, null, values);
    }

    public void removeResult(MeasurementResult result) {
        String uuidString = result.getId().toString();

        mDatabase.delete(ResultsTable.NAME, ResultsTable.Cols.UUID + " = ?",
                new String[] { uuidString });
//        MeasurementResult result = getMeasurementResult(resultId)
//        mMeasurementResults.remove(result);
    }

    private static ContentValues getContentValues(MeasurementResult result) {
        ContentValues values = new ContentValues();
        values.put(ResultsTable.Cols.UUID, result.getId().toString());
        values.put(ResultsTable.Cols.SYS, result.getSysBloodPressure());
        values.put(ResultsTable.Cols.DIA, result.getDiaBloodPressure());
        values.put(ResultsTable.Cols.PULSE, result.getPulse());
        values.put(ResultsTable.Cols.ARRHYTHMIA, result.isArrhythmia()? 1 : 0);
        values.put(ResultsTable.Cols.ARM, result.getArm());
        values.put(ResultsTable.Cols.COMMENT, result.getComment());
        values.put(ResultsTable.Cols.DATE, result.getDate().getTime());

        return values;
    }
}
