package me.dayman.getup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

import me.dayman.getup.R;
import me.dayman.getup.repository.models.Alarm;

/**
 * Created by Daymannovaes on 24/12/2014.
 */
public class AlarmListAdapter extends BaseAdapter {
    private Context context;
    private List<Alarm> list;

    public AlarmListAdapter(Context context, List<Alarm> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }
    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return list.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Alarm alarm = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_alarm_list, null);

        EditText alarmValue = (EditText) view.findViewById(R.id.list_alarmValue);
        EditText repeat = (EditText) view.findViewById(R.id.list_repeat);
        EditText type = (EditText) view.findViewById(R.id.list_type);

        alarmValue.setText(alarm.toString());
        repeat.setText(alarm.isRepeat() ? "yes" : "no");
        type.setText("nfc");

        return view;
    }
}
