package me.dayman.getup.repository.logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import me.dayman.getup.R;
import me.dayman.getup.activities.AlarmActivity;
import me.dayman.getup.repository.Repository;
import me.dayman.getup.repository.models.Alarm;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class AlarmLogic {
    public static void setAlarmByView(View alarmPicker, Context context) {
        TimePicker timePicker = (TimePicker)alarmPicker.findViewById(R.id.timepicker);
        CheckBox checkbox = (CheckBox)alarmPicker.findViewById(R.id.repeat);

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        boolean repeat = checkbox.isChecked();

        setAndroidAlarm(hour, minute, context);
        setDatabaseAlarm(hour, minute, repeat);
    }

    private static void setAndroidAlarm(int hour, int minute, Context context) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = getPreparedCalendar(hour, minute);

        Intent it = new Intent("me.dayman.getup.EXECUTE_ALARM");
        PendingIntent p = PendingIntent.getActivity(context, 0, it, 0);

        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);

        Toast.makeText(context, "Alarm set for " + formatZero(hour) + ":" + formatZero(minute), Toast.LENGTH_SHORT).show();
    }
    private static Calendar getPreparedCalendar(int hour, int minute) {
        Calendar c = Calendar.getInstance();

        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        return c;
    }



    private static void setDatabaseAlarm(int hour, int minute, boolean repeat) {
        Alarm alarm = new Alarm(
                hour, minute, repeat, "nfc"
        );

        Repository.getAdapter().store(alarm);
    }

    public static List<Alarm> getAlarmsFromDB() {
        return Repository.getAdapter().findAll(Alarm.class);
    }

    public static String formatZero(int value) {
        return value < 10 ? "0" + value : "" + value;
    }
}
