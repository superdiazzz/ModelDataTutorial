package com.example.android.data.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.data.model.DataItem;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open(){
        mDatabase = mDbHelper.getWritableDatabase();

    }
    public void close(){
        mDbHelper.close();
    }

    public DataItem createItem(DataItem item){

        ContentValues contentValues = item.toContentValues();
        mDatabase.insert(ItemTables.TABLE_ITEMS, null, contentValues);

        return  item;
    }

    public  long getDataItemCount(){
        return DatabaseUtils.queryNumEntries(mDatabase, ItemTables.TABLE_ITEMS);
    }

    public void seedDatabase(List<DataItem> items){
        long numItems = getDataItemCount();
        if(numItems == 0){
            // akan dibuatkan
            for (DataItem item: items) {
                try {
                    createItem(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }

        }else{
            // sudah ada
        }
    }

    public List<DataItem> getAllItems(){
        List<DataItem> items = new ArrayList<>();
        Cursor cursor = mDatabase.query(ItemTables.TABLE_ITEMS, ItemTables.ALL_COLUMNS, null, null, null, null, null);

        while (cursor.moveToNext()) {
            DataItem item = new DataItem();
            item.setItemID(cursor.getString(
                    cursor.getColumnIndex(ItemTables.COLUMN_ID)));
            item.setItemName(cursor.getString(
                    cursor.getColumnIndex(ItemTables.COLUMN_NAME)));
            item.setDescription(cursor.getString(
                    cursor.getColumnIndex(ItemTables.COLUMN_DESCRIPTION)));
            item.setCategory(cursor.getString(
                    cursor.getColumnIndex(ItemTables.COLUMN_CATEGORY)));
            item.setSortPotition(cursor.getInt(
                    cursor.getColumnIndex(ItemTables.COLUMN_ID)));
            item.setPrice(cursor.getDouble(
                    cursor.getColumnIndex(ItemTables.COLUMN_PRICE)));
            item.setImage(cursor.getString(
                    cursor.getColumnIndex(ItemTables.COLUMN_IMAGE)));
            items.add(item);
        }

        return items;
    }

}
