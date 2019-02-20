package com.bikshanov.bloodpressurelog;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Locale;

/**
 * Created by Peter Bikshanov on 12.03.2017.
 */

public class Helpers {

    public static String formatFullDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault());

        return df.format(date);
    }

    static String formatShortDate(Context context, Date date) {
        DateFormat df = android.text.format.DateFormat.getDateFormat(context);
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        return df.format(date);
    }

    static String formatTime(Context context, Date date) {
        DateFormat df = android.text.format.DateFormat.getTimeFormat(context);
//        DateFormat df = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        return df.format(date);
    }

    // Select the color for item depending the blood pressure value
    public static int pressureToColor(Context context, int sysPressure, int diaPressure) {

        final int NORMAL = 100;
        final int ELEVATED = 200;
        final int STAGE1 = 300;
        final int STAGE2 = 400;
        final int STAGE3 = 500;
        final int OPTIMAL = 600;

        int sys = NORMAL;
        int dia = NORMAL;
        int pressure = NORMAL;

        if (sysPressure >= 120 && sysPressure <= 129) {
            sys = NORMAL;
        } else if (sysPressure >= 130 && sysPressure <= 139) {
            sys = ELEVATED;
        } else if (sysPressure >= 140 && sysPressure <= 159) {
            sys = STAGE1;
        } else if (sysPressure >= 160 && sysPressure <= 179) {
            sys = STAGE2;
        } else if (sysPressure >= 180) {
            sys = STAGE3;
        }

        if (diaPressure >= 80 && diaPressure <= 84) {
            dia = NORMAL;
        }else if (diaPressure >= 85 && diaPressure <= 89) {
            dia = ELEVATED;
        } else if (diaPressure >= 90 && diaPressure <= 99) {
            dia = STAGE1;
        } else if (diaPressure >= 100 && diaPressure <= 109) {
            dia = STAGE2;
        } else if (diaPressure >= 110) {
            dia = STAGE3;
        } else if (diaPressure < 80) {
            dia = OPTIMAL;
        }

        if (sys > dia || sys == dia) {
            pressure = sys;
        } else if (sys < dia) {
            pressure = dia;
        }

        if (sysPressure < 120 && diaPressure < 80) {
            pressure = OPTIMAL;
        }

        if (pressure == NORMAL) {
            return ContextCompat.getColor(context, R.color.light_green);
        } else if (pressure == ELEVATED) {
            return ContextCompat.getColor(context, R.color.yellow);
        } else if (pressure == STAGE1) {
            return ContextCompat.getColor(context, R.color.orange);
        } else if (pressure == STAGE2) {
            return ContextCompat.getColor(context, R.color.red);
        } else if (pressure == STAGE3) {
            return ContextCompat.getColor(context, R.color.deep_red);
        }

        return ContextCompat.getColor(context, R.color.green);
    }

    public static int getPressureCategory(int sysPressure, int diaPressure) {

        final int NORMAL = 100;
        final int ELEVATED = 200;
        final int STAGE1 = 300;
        final int STAGE2 = 400;
        final int STAGE3 = 500;
        final int OPTIMAL = 600;

        int sys = NORMAL;
        int dia = NORMAL;
        int pressure = NORMAL;

        if (sysPressure >= 120 && sysPressure <= 129) {
            sys = NORMAL;
        } else if (sysPressure >= 130 && sysPressure <= 139) {
            sys = ELEVATED;
        } else if (sysPressure >= 140 && sysPressure <= 159) {
            sys = STAGE1;
        } else if (sysPressure >= 160 && sysPressure <= 179) {
            sys = STAGE2;
        } else if (sysPressure >= 180) {
            sys = STAGE3;
        }

        if (diaPressure >= 80 && diaPressure <= 84) {
            dia = NORMAL;
        }else if (diaPressure >= 85 && diaPressure <= 89) {
            dia = ELEVATED;
        } else if (diaPressure >= 90 && diaPressure <= 99) {
            dia = STAGE1;
        } else if (diaPressure >= 100 && diaPressure <= 109) {
            dia = STAGE2;
        } else if (diaPressure >= 110) {
            dia = STAGE3;
        } else if (diaPressure < 80) {
            dia = OPTIMAL;
        }

        if (sys > dia || sys == dia) {
            pressure = sys;
        } else if (sys < dia) {
            pressure = dia;
        }

        if (sysPressure < 120 && diaPressure < 80) {
            pressure = OPTIMAL;
        }

        return pressure;
    }


    public static boolean isEmpty(@NonNull EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }

    public static boolean isNumberValid(Editable text) {
        String input = text.toString().trim();
        return !(input.length() == 0) && (Integer.parseInt(input.toString()) >= 30)
                && (Integer.parseInt(input.toString()) <= 300);
    }
}