package com.example.chmanish.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    EditText etEditTextAdd;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        etEditTextAdd = (EditText) findViewById(R.id.etEditTextAdd);
        sp = (Spinner)findViewById(R.id.spPriority);
    }
    public void onSaveAddItem(View view) {
        String s = etEditTextAdd.getText().toString();
        if (!s.isEmpty()){
            itemRecord i = new itemRecord(s);
            String value = sp.getSelectedItem().toString();
            if (value.equals("LOW") || value.equals("Low")) {
                i.setTaskPriority(itemRecord.priority.LOW);
            } else if (value.equals("MEDIUM") || value.equals("Medium")) {
                i.setTaskPriority(itemRecord.priority.MEDIUM);
            } else if (value.equals("HIGH") || value.equals("High")) {
                i.setTaskPriority(itemRecord.priority.HIGH);
            }

            Intent data = new Intent();
            data.putExtra("newItem", i);
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
