package com.bikshanov.bloodpressurelog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bikshanov.bloodpressurelog.database.ResultsDbSchema.ResultsTable;

import androidx.annotation.Nullable;

public class ResultsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "resultsBase.db";

    public ResultsBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + ResultsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ResultsTable.Cols.UUID + ", " + ResultsTable.Cols.SYS + ", " +
                ResultsTable.Cols.DIA + ", " + ResultsTable.Cols.PULSE + ", " +
                ResultsTable.Cols.ARRHYTHMIA + ", " + ResultsTable.Cols.ARM + ", " +
                ResultsTable.Cols.COMMENT + ", " + ResultsTable.Cols.DATE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
