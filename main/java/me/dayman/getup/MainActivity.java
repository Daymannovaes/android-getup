package me.dayman.getup;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
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
import me.dayman.getup.repository.models.Deactivator;
import me.dayman.getup.util.Dispatcher;
import me.dayman.getup.util.Util;


public class MainActivity extends Activity {
    private Dispatcher dispatcher;
    private MaterialDialog nfcDialog, alarmDialog;
    private boolean dialogIsOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildMainFields();
        initializeRepository();

        setContentView(R.layout.activity_main);
    }

    // ---- BUILDER METHODS ---------------------------
    private void buildMainFields() {
        buildDispatcher();
        buildDialogs();
    }

    private void buildDispatcher() {
        dispatcher = new Util.Dispatcher(this);
    }

    public void buildDialogs() {
        alarmDialog = Util.DialogBuilder.alarm(this);
        nfcDialog = Util.DialogBuilder.nfc(this, dispatcher);
    }

    public void initializeRepository() {
        try {
            DatabaseSpec database = PersistenceConfig.registerSpec(1);
            database.match(Alarm.class);
            database.match(Deactivator.class);

            Repository.setAdapter(Persistence.getAdapter(this));
        } catch (Exception e) {

        }
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
            saveNewNfcId(nfcId);
        }
    }

    private void saveNewNfcId(String nfcId) {
        MasterDeactivator.setDeactivator(nfcId);
        setNfcView(nfcId);

        SharedPreferences.Editor editor = getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit();
        editor.putString("masterDeactivator", nfcId);

        editor.apply();
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
