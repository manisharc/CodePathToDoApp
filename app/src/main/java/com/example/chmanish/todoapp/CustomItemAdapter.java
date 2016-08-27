package com.example.chmanish.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chmanish on 8/27/16.
 */
public class CustomItemAdapter extends ArrayAdapter<itemRecord> {
    public CustomItemAdapter(Context context, ArrayList<itemRecord> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        itemRecord i = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskPriority = (TextView) convertView.findViewById(R.id.tvTaskPriority);
        // Populate the data into the template view using the data object
        tvTaskName.setText(i.getTaskDescription());
        tvTaskPriority.setText(i.getTaskPriority().toString());
        // Return the completed view to render on screen
        return convertView;
    }


}
