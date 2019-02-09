package com.bikshanov.bloodpressurelog.database;

public class ResultsDbSchema {

    public static final class ResultsTable {
        public static final String NAME = "results";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String SYS = "sys";
            public static final String DIA = "dia";
            public static final String PULSE = "pulse";
            public static final String ARRHYTHMIA = "arrhythmia";
            public static final String ARM = "arm";
            public static final String COMMENT = "comment";
            public static final String DATE = "date";
        }
    }
}
