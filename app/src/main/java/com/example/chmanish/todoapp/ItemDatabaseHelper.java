package com.example.chmanish.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class with the table definitions and datebase apis
 */
public class ItemDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoItemsDB";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_ITEMS = "itemRecords";

    // Item Table Columns
    private static final String KEY_ITEM_ID = "_id";
    private static final String KEY_ITEM_DESCRIPTION = "itemDescription";
    private static final String KEY_ITEM_PRIORITY = "itemPriority";
    private static final String KEY_ITEM_YEAR = "itemDueDateYear";
    private static final String KEY_ITEM_MONTH = "itemDueDateMonth";
    private static final String KEY_ITEM_DAY = "itemDueDateDay";

    private static ItemDatabaseHelper sInstance;

    public static synchronized ItemDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ItemDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                KEY_ITEM_DESCRIPTION + " TEXT, " +
                KEY_ITEM_PRIORITY + " INTEGER," +
                KEY_ITEM_YEAR + " INTEGER," +
                KEY_ITEM_MONTH + " INTEGER," +
                KEY_ITEM_DAY + " INTEGER" +
                ")";


        db.execSQL(CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    public long addItem(itemRecord item) {
        SQLiteDatabase db = getWritableDatabase();
        long itemId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_DESCRIPTION, item.getTaskDescription());
            values.put(KEY_ITEM_PRIORITY, item.getTaskPriority());
            values.put(KEY_ITEM_YEAR, item.getTaskDueDateYear());
            values.put(KEY_ITEM_MONTH, item.getTaskDueDateMonth());
            values.put(KEY_ITEM_DAY, item.getTaskDueDateDay());

            itemId = db.insertOrThrow(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.d("db", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return itemId;
    }

    public itemRecord getItem(int id){
        SQLiteDatabase db = getReadableDatabase();
        String SELECT_QUERY =
                String.format("SELECT _id as _id,* FROM %s WHERE %s=%d", TABLE_ITEMS, KEY_ITEM_ID, id);
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    itemRecord item = new itemRecord();
                    item.setTaskDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
                    item.setTaskPriority(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_PRIORITY)));
                    item.setDate(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_YEAR)),
                            cursor.getInt(cursor.getColumnIndex(KEY_ITEM_MONTH)),
                            cursor.getInt(cursor.getColumnIndex(KEY_ITEM_DAY)));
                    return item;
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("db", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return null;
    }

    // Gets all the items in ascending order of priority to be displayed in ListView
    public Cursor getAllItemsAscending(){
        SQLiteDatabase db = getWritableDatabase();
        String SELECT_QUERY =
                String.format("SELECT _id as _id,* FROM %s ORDER BY %s ASC",TABLE_ITEMS,KEY_ITEM_PRIORITY);
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        return cursor;
    }

    // Deletes the item with the given id
    public void deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_ITEMS, KEY_ITEM_ID + "=" + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("db", "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}