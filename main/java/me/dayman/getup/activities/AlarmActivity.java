package me.dayman.getup.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.dayman.getup.R;
import me.dayman.getup.repository.logic.MasterDeactivator;
import me.dayman.getup.util.Dispatcher;
import me.dayman.getup.util.Util;

public class AlarmActivity extends Activity {
    private Dispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        dispatcher = new Dispatcher(this);

       /* Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play(); */
    }

    @Override
    protected void onResume() {
        super.onResume();

        setDeactivatorDefaultValue();
        dispatcher.enableForegroundDispatch();
    }

    private void setDeactivatorDefaultValue() {
        String preferenceId = getDeactivatorFromPreference();

        MasterDeactivator.setDeactivator(preferenceId); //real id or empty string
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String nfcId = Util.Nfc.getNfcId(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            handleNfcIntent(nfcId);
        }
    }

    private void handleNfcIntent(String nfcId) {
        if(MasterDeactivator.match(nfcId))
            handleCorrectNfcId();
    }

    private void handleCorrectNfcId() {
        Toast.makeText(this, "Your nfc id is correct", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDeactivatorFromPreference() {
        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        return preferences.getString("masterDeactivator", "");

    }
}
