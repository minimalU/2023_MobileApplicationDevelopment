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

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "party.db";
    public static final int DB_VERSION = 1;

    // planner
    public static final String PARTY_TABLE = "PARTY_TABLE";
    public static final String CL_PARTY_DATE = "PARTY_DATE";
    public static final String CL_PARTY_TIME = "PARTY_TIME";
    public static final String CL_PARTY_THEME = "PARTY_THEME";
    public static final String CL_PARTY_BUDGET = "PARTY_BUDGET";
    public static final String CL_PARTY_GUEST = "PARTY_GUEST";
    public static final String CL_ID = "ID";
    public static final String[] CL_ALL = { CL_PARTY_DATE, CL_PARTY_TIME, CL_PARTY_THEME, CL_PARTY_BUDGET, CL_PARTY_GUEST };
    //

    // planner
    public static final String GUEST_TABLE = "GUEST_TABLE";
    public static final String GL_GUEST_ID = "GUEST_ID";
    public static final String GL_GUEST_NAME = "GUEST_NAME";
    public static final String GL_GUEST_PHONE = "GUEST_PHONE";

    public static final String[] GUEST_ALL = { GL_GUEST_ID, GL_GUEST_NAME, GL_GUEST_PHONE };
    //

    private static final String CREATE_LIST_TABLE =
            "CREATE TABLE " + PARTY_TABLE +
                    "(" + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CL_PARTY_DATE + " TEXT, " +
                    CL_PARTY_TIME + " TEXT, " +
                    CL_PARTY_THEME + " TEXT, " +
                    CL_PARTY_BUDGET + " FLOAT, " +
                    CL_PARTY_GUEST  + " INT)";

    private static final String CREATE_GUEST_TABLE =
            "CREATE TABLE " + GUEST_TABLE +
                    "(" + GL_GUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GL_GUEST_NAME + " TEXT, " +
                    GL_GUEST_PHONE + " TEXT)";
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_GUEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }

    // add party schedule
    public long addItem (PartyData partyData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues content_value = new ContentValues();

        content_value.put(CL_PARTY_DATE, partyData.getDate());
        content_value.put(CL_PARTY_TIME, partyData.getTime());
        content_value.put(CL_PARTY_THEME, partyData.getTheme());
        content_value.put(CL_PARTY_BUDGET, partyData.getBudget());
        content_value.put(CL_PARTY_GUEST, partyData.getGuest());

        long retValue = database.insert(PARTY_TABLE, null, content_value);

        return retValue;
    }

    // update party schedule
    public int updateItem(PartyData partyData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues content_value = new ContentValues();

        content_value.put(CL_PARTY_DATE, partyData.getDate());
        content_value.put(CL_PARTY_TIME, partyData.getTime());
        content_value.put(CL_PARTY_THEME, partyData.getTheme());
        content_value.put(CL_PARTY_BUDGET, partyData.getBudget());
        content_value.put(CL_PARTY_GUEST, partyData.getGuest());

        String selection = CL_ID + "=?";
        String[] selectionArgs = { String.valueOf(partyData.getId()) };

        int updatedRow = database.update(PARTY_TABLE, content_value, selection, selectionArgs);
        return updatedRow;
    }

    public int updateItem (ContentValues content_value, String selection, String[] selectionArgs) {
        SQLiteDatabase database = this.getWritableDatabase();
        int updatedRow = database.update(PARTY_TABLE, content_value, selection, selectionArgs);

        return updatedRow;
    }

    public int getID (PartyData partyData) {
        SQLiteDatabase database = this.getWritableDatabase();
        int id = partyData.getId();
        return id;
    }

    // delete party schedule
    public int deleteItem (int listID) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryDelete = "DELETE FROM " + PARTY_TABLE + " WHERE " + CL_ID + "=" + String.valueOf(listID);
        // rawQuery public Cursor rawQuery
        int rowDeleted = database.rawQuery(queryDelete, null).getCount();

        return rowDeleted;
    }

    public int deleteItem (String selection, String[] selectionArgs) {
        SQLiteDatabase database = this.getWritableDatabase();
        int rowDeleted = database.delete(PARTY_TABLE, selection, selectionArgs);

        return rowDeleted;
    }


    // public methods
    public List<PartyData> getSchedule() {
        List<PartyData> list = new ArrayList<>();

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

                PartyData newSchedule = new PartyData(pID, pDate, pTime, pTheme, pBudget, pGuest);
                list.add(newSchedule);
            } while (cursor.moveToNext());
        }
        else {

        }
        return list;
    }
}
