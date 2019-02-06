package com.bikshanov.bloodpressurelog;

import android.content.Context;
import android.text.Editable;
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

    static String formatShortDate(Date date) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

        return df.format(date);
    }

    static String formatTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return df.format(date);
    }

    // Select the color for item depending the blood pressure value
    public static int pressureToColor(Context context, int sysPressure, int diaPressure) {

        final int NORMAL = 100;
        final int ELEVATED = 200;
        final int STAGE1 = 300;
        final int STAGE2 = 400;
        final int STAGE3 = 500;

        int sys = NORMAL;
        int dia = NORMAL;
        int pressure = NORMAL;

        if (sysPressure >= 130 && sysPressure <= 139) {
            sys = ELEVATED;
        } else if (sysPressure >= 140 && sysPressure <= 159) {
            sys = STAGE1;
        } else if (sysPressure >= 160 && sysPressure <= 179) {
            sys = STAGE2;
        } else if (sysPressure >= 180) {
            sys = STAGE3;
        } else if (sysPressure < 130) {
            sys = NORMAL;
        }

        if (diaPressure >= 85 && diaPressure <= 89) {
            dia = ELEVATED;
        } else if (diaPressure >= 90 && diaPressure <= 99) {
            dia = STAGE1;
        } else if (diaPressure >= 100 && diaPressure <= 109) {
            dia = STAGE2;
        } else if (diaPressure >= 110) {
            dia = STAGE3;
        } else if (diaPressure < 85) {
            dia = NORMAL;
        }

        if (sys > dia || sys == dia) {
            pressure = sys;
        } else if (sys < dia) {
            pressure = dia;
        }

        if (pressure == ELEVATED) {
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