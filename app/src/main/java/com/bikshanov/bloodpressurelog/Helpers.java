package com.bikshanov.bloodpressurelog;

import android.graphics.Color;

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
    public static int pressureToColor(int pressure) {

        if (pressure >= 130 && pressure <= 139) {
            return Color.YELLOW;
        } else if (pressure > 139) {
            return Color.RED;
        }

        return Color.GREEN;
    }

}
