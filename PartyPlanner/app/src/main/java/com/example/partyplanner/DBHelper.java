// FILE         : DBHelper.java
// PROJECT      : A2PartyPlanner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-10
// DESCRIPTION  : This file is SQLiteOpenHelper class file.
// It creates the data base table and add data into a table
// and delete data from a table and get data as an ArrayList.
// REFERENCE    : 7_sql_database
// https://www.youtube.com/watch?v=toDZ9X5uSXw&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=89
// https://www.youtube.com/watch?v=312RhjfetP8


package com.example.partyplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "party.db";
    public static final String PARTY_TABLE = "PARTY_TABLE";
    public static final String CL_PARTY_DATE = "PARTY_DATE";
    public static final String CL_PARTY_TIME = "PARTY_TIME";
    public static final String CL_PARTY_THEME = "PARTY_THEME";
    public static final String CL_PARTY_BUDGET = "PARTY_BUDGET";
    public static final String CL_PARTY_GUEST = "PARTY_GUEST";
    public static final String CL_ID = "ID";

    public static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_TABLE = "CREATE TABLE " + PARTY_TABLE +
                "(" + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CL_PARTY_DATE + " TEXT, " +
                CL_PARTY_TIME + " TEXT, " +
                CL_PARTY_THEME + " TEXT, " +
                CL_PARTY_BUDGET + " FLOAT, " +
                CL_PARTY_GUEST  + " INT)";

        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // add party schedule
    public boolean addItem (ScheduleData scheduleData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues content_value = new ContentValues();

        content_value.put(CL_PARTY_DATE, scheduleData.getDate());
        content_value.put(CL_PARTY_TIME, scheduleData.getTime());
        content_value.put(CL_PARTY_THEME, scheduleData.getTheme());
        content_value.put(CL_PARTY_BUDGET, scheduleData.getBudget());
        content_value.put(CL_PARTY_GUEST, scheduleData.getGuest());

        long retValue = database.insert(PARTY_TABLE, null, content_value);
        if (retValue == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    // delete party schedule
    public int getID (ScheduleData scheduleData) {
        SQLiteDatabase database = this.getWritableDatabase();
        int id = scheduleData.getId();
        return id;
    }

    // delete party schedule
    public boolean deleteItem (int listID) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryDelete = "DELETE FROM " + PARTY_TABLE + " WHERE " + CL_ID + "=" + String.valueOf(listID);

        // rawQuery public Cursor rawQuery
        Cursor cursor = database.rawQuery(queryDelete, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }


    // public methods
    public List<ScheduleData> getSchedule() {
        List<ScheduleData> list = new ArrayList<>();

        String query = "SELECT * FROM " + PARTY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        // rawQuery public Cursor rawQuery
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                int pID = cursor.getInt(0);
                String pDate = cursor.getString(1);
                String pTime = cursor.getString(2);
                String pTheme = cursor.getString(3);
                float pBudget = cursor.getFloat(4);
                int pGuest = cursor.getInt(5);

                ScheduleData newSchedule = new ScheduleData(pID, pDate, pTime, pTheme, pBudget, pGuest);
                list.add(newSchedule);
            } while (cursor.moveToNext());
        }
        else {

        }
        return list;
    }
}
