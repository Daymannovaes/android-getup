package me.dayman.getup.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.nfc.NfcAdapter;

/**
 * Created by Daymannovaes on 23/12/2014.
 */
public class Dispatcher {
    private Activity activity;
    private NfcAdapter adapter;
    private PendingIntent pendingIntent;

    public Dispatcher(Activity activity, NfcAdapter adapter, PendingIntent pendingIntent) {
        this.activity = activity;
        this.adapter = adapter;
        this.pendingIntent = pendingIntent;
    }

    public void disableForegroundDispatch() {
        adapter.disableForegroundDispatch(activity);
    }
    public void enableForegroundDispatch() {
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }
}
