package me.dayman.getup;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.PersistenceConfig;

import me.dayman.getup.activities.AlarmListActivity;
import me.dayman.getup.repository.Repository;
import me.dayman.getup.repository.logic.AlarmLogic;
import me.dayman.getup.repository.logic.MasterDeactivator;
import me.dayman.getup.repository.models.Alarm;
import me.dayman.getup.repository.models.DeactivatorModel;
import me.dayman.getup.util.Dispatcher;
import me.dayman.getup.util.Util;


public class MainActivity extends Activity {
    private Dispatcher dispatcher;
    private MaterialDialog nfcDialog, alarmDialog;
    private AlarmManager am;
    private boolean dialogIsOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildMainFields();
        initializeRepository();

        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        setContentView(R.layout.activity_main);
    }

    // ---- BUILDER METHODS ---------------------------
    private void buildMainFields() {
        buildNfcDialog();
        buildAlarmDialog();

        buildDispatcher();
    }

    private void buildDispatcher() {
        dispatcher = new Dispatcher(this, buildAdapter(), buildPendindIntent());
    }

    private NfcAdapter buildAdapter() {
        return NfcAdapter.getDefaultAdapter(this);
    }

    private PendingIntent buildPendindIntent() {
        return PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    public void buildAlarmDialog() {
        final TimePicker timePicker = new TimePicker(this);

        alarmDialog = new MaterialDialog.Builder(this)
                .customView(timePicker)
                .title("Pick the time")
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.ok)
                .callback(new MaterialDialog.SimpleCallback() {
                    @Override
                    public void onPositive(MaterialDialog materialDialog) {
                        AlarmLogic.setAlarmByView(timePicker, am, MainActivity.this);

                        //sendBroadcast(it);

                    }
                })
                .build();
    }

    public void buildNfcDialog() {
        nfcDialog = new MaterialDialog.Builder(this)
                .title("Master deactivator")
                .content("scan your nfc tag")
                .negativeText(android.R.string.cancel)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dispatcher.disableForegroundDispatch();

                        dialogIsOpened = false;
                    }
                }).build();
    }

    public void initializeRepository() {
        DatabaseSpec database = PersistenceConfig.registerSpec(1);
        database.match(Alarm.class);
        database.match(DeactivatorModel.class);

        Repository.setAdapter(Persistence.getAdapter(this));
    }

    protected void setAlarmByView(View findViewById) {
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

// ----------------------------------------------
// ----------------------------------------------


    // ---- EVENT METHODS ---------------------------
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);

        nfcDialog.dismiss();

        handleNfcIntent(intent);
    }

    private void handleNfcIntent(Intent intent) {
        String nfcId = Util.Nfc.getNfcId(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            MasterDeactivator.setDeactivator(nfcId);
            setNfcView(nfcId);
        }
    }

    public void onScanClick(View v) {
        dispatcher.enableForegroundDispatch();

        dialogIsOpened = true;
        nfcDialog.show();
    }

    public void onSetAlarmClick(View v) {
        alarmDialog.show();
    }
    public void onSeeAlarmsClick(View v) {
        Intent it = new Intent(this, AlarmListActivity.class);
        startActivity(it);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(dialogIsOpened)
            dispatcher.enableForegroundDispatch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
// ----------------------------------------------
// ----------------------------------------------


    // ---- NFC METHODS -----------------------------
    private void setNfcView(String nfcId) {
        ((TextView) findViewById(R.id.masterNfc)).setText("The master nfc id is " + nfcId);
    }

}