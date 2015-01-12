package me.dayman.getup.util;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import me.dayman.getup.R;
import me.dayman.getup.repository.Repository;
import me.dayman.getup.repository.models.Alarm;

/**
 * Created by Daymannovaes on 24/12/2014.
 */
public class AlarmListAdapter extends BaseAdapter implements ExpandableListAdapter {
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

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return list.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final Alarm alarm = list.get(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_alarm_list_group, null);

        TextView alarmValue = (TextView) view.findViewById(R.id.list_alarmValue);

        alarmValue.setText(alarm.toString());

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Alarm alarm = list.get(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_alarm_list_child, null);

        TextView type = (TextView) view.findViewById(R.id.list_type);
        CheckBox repeat = (CheckBox) view.findViewById(R.id.list_repeat);

        Button delete = (Button) view.findViewById(R.id.list_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(groupPosition);
                Repository.getAdapter().delete(alarm);
                notifyDataSetChanged();
            }
        });

        repeat.setChecked(alarm.isRepeat());
        type.setText("nfc");

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
