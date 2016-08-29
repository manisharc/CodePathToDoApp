package com.example.chmanish.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    ItemDatabaseHelper handler;
    Cursor todoCursor;
    ToDoCursorAdapter toDoCursorAdapter;

    ListView lvItems;

    // REQUEST_CODE to differentiate between the ADD and EDIT views
    private final int REQUEST_CODE_EDIT = 20;
    private final int REQUEST_CODE_ADD = 21;

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

        // setup database
        handler = ItemDatabaseHelper.getInstance(this);

        todoCursor = handler.getAllItemsAscending();
        toDoCursorAdapter = new ToDoCursorAdapter(this, todoCursor);

        lvItems.setAdapter(toDoCursorAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                handler.deleteItem((int)id);
                refreshView();
                return true;
            }
        });


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, AddTask.class);
                itemRecord item = handler.getItem((int)id);
                i.putExtra("existingItem", item);
                i.putExtra("existingItemId", (int)id);
                startActivityForResult(i, REQUEST_CODE_EDIT);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void refreshView(){
        todoCursor = handler.getAllItemsAscending();
        toDoCursorAdapter.changeCursor(todoCursor);

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
        Intent i = new Intent(MainActivity.this, AddTask.class);
        startActivityForResult(i, REQUEST_CODE_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT) {
            // Extract name value from result extras
            itemRecord i = (itemRecord) data.getSerializableExtra("existingItemUpdates");
            int id = (int) data.getSerializableExtra("existingItemId");
            if (i!= null){
                handler.deleteItem((int)id);
                handler.addItem(i);
                // Update to get directly from the db
                /*itemRecord updateItem = cupboard().withDatabase(db).get(itemRecord.class, i.get_id());
                updateItem.setTaskPriority(i.getTaskPriority());
                updateItem.setTaskDescription(i.getTaskDescription());
                cupboard().withDatabase(db).put(updateItem);*/
                //handler.addOrUpdateItem(i);
                refreshView();

            }

        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD) {
            itemRecord i = (itemRecord) data.getSerializableExtra("newItem");
            handler.addItem(i);
            refreshView();
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
        todoCursor.close();

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
