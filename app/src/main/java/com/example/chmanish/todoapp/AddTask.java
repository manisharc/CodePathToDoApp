package com.example.chmanish.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    boolean isEditTask = false;
    EditText etEditTextAdd;
    int existingItemId;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        etEditTextAdd = (EditText) findViewById(R.id.etEditTextAdd);
        sp = (Spinner)findViewById(R.id.spPriority);

        itemRecord i = (itemRecord) getIntent().getSerializableExtra("existingItem");
        if (i != null){
            // If this was called to edit a task
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
        }
        else {
            //Set it to the first element of the array
            sp.setSelection(0);

        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

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
