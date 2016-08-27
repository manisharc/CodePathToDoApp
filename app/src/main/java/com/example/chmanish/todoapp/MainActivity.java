package com.example.chmanish.todoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db;
    ArrayList<itemRecord> todoItems;
    ArrayList<String> taskNameArray;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        etEditText = (EditText) findViewById(R.id.etEditText);

        // setup database
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);
        db = dbHelper.getWritableDatabase();

        // here is where you associate the name array.
        taskNameArray = getAllTasksNames();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskNameArray);
        lvItems.setAdapter(aToDoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemRecord i = todoItems.get(position);
                cupboard().withDatabase(db).delete(itemRecord.class, i.get_id());
                todoItems.remove(position);
                taskNameArray.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("itemName", todoItems.get(position).taskDescription);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private static List<itemRecord> getListFromQueryResultIterator(QueryResultIterable<itemRecord> iter) {

        final List<itemRecord> tasks = new ArrayList<itemRecord>();
        for (itemRecord t : iter) {
            tasks.add(t);
        }
        iter.close();

        return tasks;
    }

    public ArrayList<String> getAllTasksNames() {
        final QueryResultIterable<itemRecord> iter = cupboard().withDatabase(db).query(itemRecord.class).query();
        todoItems = (ArrayList<itemRecord>) getListFromQueryResultIterator(iter);

        ArrayList<String> taskNameArray = new ArrayList<String>();
        for (itemRecord b : todoItems) {
            taskNameArray.add(b.getTaskDescription());
        }

        return taskNameArray;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        String s = etEditText.getText().toString();
        if (!s.isEmpty()){
            itemRecord i = new itemRecord(s);
            cupboard().withDatabase(db).put(i);
            todoItems.add(i);
            taskNameArray.add(i.getTaskDescription());
            //aToDoAdapter.add(i.getTaskDescription());
            aToDoAdapter.notifyDataSetChanged();
            // Empty the edit text
            etEditText.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "no empty tasks", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("itemNameUpdated");
            int position = data.getExtras().getInt("position", 0);
            itemRecord i = todoItems.get(position);
            i.setTaskDescription(name);
            cupboard().withDatabase(db).put(i);
            todoItems.set(position, i);
            taskNameArray.set(position, i.getTaskDescription());
            aToDoAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.chmanish.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.chmanish.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
