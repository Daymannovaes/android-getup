package me.dayman.getup.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class Dispatcher {
    private Activity activity;
    private NfcAdapter adapter;
    private PendingIntent pendingIntent;

    public Dispatcher(Activity activity) {
        this.activity = activity;
        this.adapter = NfcAdapter.getDefaultAdapter(activity);
        this.pendingIntent = buildPendindIntent(activity);
    }

    private PendingIntent buildPendindIntent(Context context) {
        return PendingIntent.getActivity(context, 0,
                new Intent(context, context.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    public void disableForegroundDispatch() {
        adapter.disableForegroundDispatch(activity);
    }

    public void enableForegroundDispatch() {
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }
}
