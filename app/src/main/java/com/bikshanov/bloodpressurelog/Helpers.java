package com.bikshanov.bloodpressurelog;

import android.content.Context;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //TODO: Должно учитываться либо SYS, либо DIA
    public static int pressureToColor(Context context, int pressure) {

        if (pressure >= 120 && pressure <= 139) {
            return ContextCompat.getColor(context, R.color.yellow);
        } else if (pressure >= 140 && pressure <= 159) {
            return ContextCompat.getColor(context, R.color.orange);
        } else if (pressure >= 160 && pressure <= 179) {
            return ContextCompat.getColor(context, R.color.red);
        } else if (pressure >= 180) {
            return ContextCompat.getColor(context, R.color.deep_red);
        }

        return ContextCompat.getColor(context, R.color.green);
    }

}
