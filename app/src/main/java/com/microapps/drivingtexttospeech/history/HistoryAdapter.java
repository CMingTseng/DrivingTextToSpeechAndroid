package com.microapps.drivingtexttospeech.history;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.microapps.drivingtexttospeech.R;
import com.microapps.drivingtexttospeech.Utils;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<EventHistoryItem> {

    public HistoryAdapter(Context context, int resource, ArrayList<EventHistoryItem> events) {
        super(context, resource, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventHistoryItem event = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_item, parent, false);
            viewHolder.eventName = (TextView) convertView.findViewById(R.id.event_name);
            viewHolder.eventTimestamp = (TextView) convertView.findViewById(R.id.event_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventName.setText(event.isStartedDriving() ? "Started driving" : "Finished driving");
        viewHolder.eventName.setTextColor(ContextCompat.getColor(getContext(),
                event.isStartedDriving() ? R.color.dark_green : R.color.dark_red));
        viewHolder.eventTimestamp.setText(Utils.getDefaultTime(event.getEventTimestamp() * 1000));

        return convertView;
    }

    private static final class ViewHolder {
        TextView eventName;
        TextView eventTimestamp;
    }

}
