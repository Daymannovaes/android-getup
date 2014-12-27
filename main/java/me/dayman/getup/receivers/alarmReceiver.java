package me.dayman.getup.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import me.dayman.getup.R;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class alarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "deu certo", Toast.LENGTH_LONG).show();
        createNotification(context);
    }

    private static void createNotification(Context context) {
        final NotificationManager mgr=
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder note = new Notification.Builder(context)
                .setContentTitle("alarm")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("wake up!!!")
                .setOngoing(true)
                .setAutoCancel(false);

        mgr.notify(R.string.app_name, note.build());
    }
}
