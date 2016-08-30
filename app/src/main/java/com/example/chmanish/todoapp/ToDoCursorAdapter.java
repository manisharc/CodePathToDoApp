package com.example.chmanish.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Cursor Adapter for the Main Activity List View
 */
public class ToDoCursorAdapter extends CursorAdapter {
    public ToDoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.tvTaskName);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvTaskPriority);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("itemDescription"));
        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("itemPriority"));
        String prio = "LOW";
        // Based on the integer value, show the correct priority as well as color code them
        if (priority == itemRecord.HIGH_TO_INT) {
            prio = "HIGH";
            tvPriority.setTextColor(Color.rgb(255,0,30));
        }else if (priority == itemRecord.LOW_TO_INT) {
            prio = "LOW";
            tvPriority.setTextColor(Color.BLUE);
        }else if (priority == itemRecord.MEDIUM_TO_INT) {
            prio = "MEDIUM";
            tvPriority.setTextColor(Color.rgb(30,200,0));
        }
        // Populate fields with extracted properties
        tvBody.setText(body);
        tvPriority.setText(prio);
    }
}