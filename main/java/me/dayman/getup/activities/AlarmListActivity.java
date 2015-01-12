package me.dayman.getup.activities;

import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import me.dayman.getup.R;
import me.dayman.getup.repository.logic.AlarmLogic;
import me.dayman.getup.util.AlarmListAdapter;

public class AlarmListActivity extends ExpandableListActivity {

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AlarmListAdapter(this, AlarmLogic.getAlarmsFromDB());

        if(adapter.isEmpty())
            setEmptyView();
        else
            setListAdapter((ExpandableListAdapter)adapter);
        
        setDataSetObserver();
    }

    private void setDataSetObserver() {
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(adapter.isEmpty()) {
                    setEmptyView();
                }
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        });
    }

    private void setEmptyView() {
        TextView tx = new TextView(AlarmListActivity.this);
        tx.setText("Não há alarmes");
        tx.setTextSize(10);

        addContentView(tx, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
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
}
