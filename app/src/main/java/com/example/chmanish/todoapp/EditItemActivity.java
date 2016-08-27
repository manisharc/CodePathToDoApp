package com.example.chmanish.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditText1;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditText1 = (EditText) findViewById(R.id.etEditText1);

        String itemName = getIntent().getStringExtra("itemName");
        position = getIntent().getIntExtra("position", 0);
        etEditText1.setText(itemName);
        etEditText1.setSelection(etEditText1.getText().length());

    }

    public void onSaveItem(View view) {
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("itemNameUpdated", etEditText1.getText().toString());
        data.putExtra("position", position); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }
}
