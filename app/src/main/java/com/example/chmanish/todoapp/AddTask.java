package com.example.chmanish.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    private boolean isEditTask = false;
    private EditText etEditTextAdd;
    private int existingItemId;
    private Spinner sp;
    private DatePicker dpDueDate;
    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        etEditTextAdd = (EditText) findViewById(R.id.etEditTextAdd);
        sp = (Spinner)findViewById(R.id.spPriority);
        dpDueDate = (DatePicker)findViewById(R.id.dpDueDate);

        itemRecord i = (itemRecord) getIntent().getSerializableExtra("existingItem");
        if (i != null){
            //Edit task
            etEditTextAdd.setText(i.getTaskDescription());
            etEditTextAdd.setSelection(i.getTaskDescription().length());
            if (i.getTaskPriority() == itemRecord.LOW_TO_INT) {
                sp.setSelection(0);
            } else if (i.getTaskPriority() == itemRecord.MEDIUM_TO_INT) {
                sp.setSelection(1);
            } else
                sp.setSelection(2);
            isEditTask = true;
            existingItemId = (int)getIntent().getSerializableExtra("existingItemId");
            year = i.getTaskDueDateYear();
            month = i.getTaskDueDateMonth() - 1;
            day = i.getTaskDueDateDay();
        }
        else {
            //Add new task
            //Default setting of spinner is LOW
            sp.setSelection(0);
            // Set the DatePicker to today's date
            final Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        dpDueDate.init(year, month, day, null);

    }
    // On clicking of button to "Save" item
    public void onSaveAddItem(View view) {
        String s = etEditTextAdd.getText().toString();
        if (!s.isEmpty()){
            itemRecord i = new itemRecord(s);
            String value = sp.getSelectedItem().toString();
            if (value.equals("LOW") || value.equals("Low")) {
                i.setTaskPriority(itemRecord.LOW_TO_INT);
            } else if (value.equals("MEDIUM") || value.equals("Medium")) {
                i.setTaskPriority(itemRecord.MEDIUM_TO_INT);
            } else if (value.equals("HIGH") || value.equals("High")) {
                i.setTaskPriority(itemRecord.HIGH_TO_INT);
            }
            i.setDate(dpDueDate.getYear(), dpDueDate.getMonth() + 1, dpDueDate.getDayOfMonth());
            Intent data = new Intent();
            if (isEditTask) {
                data.putExtra("existingItemUpdates", i);
                data.putExtra("existingItemId", existingItemId);
            }
            else {
                data.putExtra("newItem", i);
            }

            // Empty the edit text
            etEditTextAdd.setText("");
            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        } else {
            Toast.makeText(getApplicationContext(), "no empty tasks", Toast.LENGTH_SHORT).show();
        }
    }


}
