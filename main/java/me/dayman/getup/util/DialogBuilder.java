package me.dayman.getup.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import me.dayman.getup.R;
import me.dayman.getup.repository.logic.AlarmLogic;

/**
 * Created by Daymannovaes on 01/01/2015.
 */
public class DialogBuilder {
    public static MaterialDialog alarm(final Context context) {
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alarmPicker = inflater.inflate(R.layout.alarm_time_picker, null);

        return new MaterialDialog.Builder(context)
                .customView(alarmPicker)
                .title("Pick the time")
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.ok)
                .callback(new MaterialDialog.SimpleCallback() {
                    @Override
                    public void onPositive(MaterialDialog materialDialog) {
                        AlarmLogic.setAlarmByView(alarmPicker, context);
                    }
                })
                .build();
    }
    public static MaterialDialog nfc(Context context, final Dispatcher dispatcher) {
        return new MaterialDialog.Builder(context)
                .title("Master deactivator")
                .content("scan your nfc tag")
                .negativeText(android.R.string.cancel)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dispatcher.disableForegroundDispatch();
                    }
                }).build();
    }
}
