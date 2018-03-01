package com.example.xu_.lab8;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xu_ on 2017/12/19.
 */

public class myDB extends SQLiteOpenHelper{
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public myDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table" + TABLE_NAME
                + "(_id integer primary key, "
                +"name text, "
                +"birth text, "
                +"gift text);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insert(String name, String birth, String gift){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("birth", birth);
        values.put("gift", gift);
        db.insert(TABLE_NAME, null, values);
        Cursor cursor = db.rawQuery("select last_insert_rowid() from "+TABLE_NAME, null);
        int temp = -1;
        if(cursor.moveToFirst()){
            temp = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return temp;

    }
    public void update(Integer id, String name, String birth, String gift){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereClause = "_id = ?";
        String[] whereArgs = {id.toString()};
        values.put("name", name);
        values.put("birth", birth);
        values.put("gift", gift);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }
    public void delete(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {id.toString()};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public boolean isExist(String name){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = {name};
        Cursor cursor = db.query(TABLE_NAME, new String[]{"name"}, whereClause, whereArgs, null, null, null);
        boolean res = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return res;
    }
    public Map<String, Object> query(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {id.toString()};
        Cursor cursor = db.query(TABLE_NAME, new String[]{"_id", "name", "birth", "gift"}, whereClause, whereArgs, null, null, null);
        ArrayList<Map<String, Object>> res = generateList(cursor);

    }
    public ArrayList<Map<String, Object>> queryAll(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "name", "birth", "gift"}, null, null, null, null, null);
        ArrayList<Item> items = new ArrayList<>();
        if(cursor.moveToNext()){
            Item item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            items.add(item);
        }
        return items;
    }
    public ArrayList<Map<String, Object>> generateList
}
