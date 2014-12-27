package me.dayman.getup.repository.logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import me.dayman.getup.repository.Repository;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class AlarmLogic {
    public static void setAlarmByView(TimePicker timePicker, AlarmManager am, Context context) {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        setAndroidAlarm(hour, minute, am, context);
        setDatabaseAlarm(hour, minute);
    }

    private static void setAndroidAlarm(int hour, int minute, AlarmManager am, Context context) {
        Calendar c = Calendar.getInstance();

        Intent it = new Intent("execute_alarm");
        PendingIntent p = PendingIntent.getBroadcast(context, 0, it, 0);

        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);

        Toast.makeText(context, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
    }



    private static void setDatabaseAlarm(int hour, int minute) {
        me.dayman.getup.repository.models.Alarm alarm = new me.dayman.getup.repository.models.Alarm(
                hour, minute, false, "nfc"
        );

        Repository.getAdapter().store(alarm);
    }

    public static List<me.dayman.getup.repository.models.Alarm> getAlarmsFromDB() {
        return Repository.getAdapter().findAll(me.dayman.getup.repository.models.Alarm.class);
    }

    public static String formatZero(int value) {
        return value < 10 ? "0" + value : "" + value;
    }
}
