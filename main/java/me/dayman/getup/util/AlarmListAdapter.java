package me.dayman.getup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import me.dayman.getup.R;
import me.dayman.getup.repository.Repository;
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

    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Alarm alarm = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_alarm_list, null);

        EditText alarmValue = (EditText) view.findViewById(R.id.list_alarmValue);
        CheckBox repeat = (CheckBox) view.findViewById(R.id.list_repeat);
        EditText type = (EditText) view.findViewById(R.id.list_type);

        Button delete = (Button) view.findViewById(R.id.list_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                Repository.getAdapter().delete(alarm);
            }
        });

        alarmValue.setText(alarm.toString());
        repeat.setChecked(alarm.isRepeat());
        type.setText("nfc");

        return view;
    }
}
