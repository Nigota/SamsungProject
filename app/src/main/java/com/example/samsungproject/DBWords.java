package com.example.samsungproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBWords {
    String DATABASE_NAME = "mydb2.db";
    int DATABASE_VERSION = 1;
    String TABLE_NAME = "MyData";

    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String COLUMN_VALUE = "value";
    String COLUMN_STAGE = "stage";
    String COLUMN_DATE = "date";

    int NUM_COLUMN_ID = 0;
    int NUM_COLUMN_NAME = 1;
    int NUM_COLUMN_VALUE = 2;
    int NUM_COLUMN_STAGE = 3;
    int NUM_COLUMN_DATE = 4;

    private SQLiteDatabase mDataBase;

    public DBWords(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, String value) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_STAGE, 0);
        cv.put(COLUMN_DATE, System.currentTimeMillis());
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Word md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, md.name);
        cv.put(COLUMN_VALUE, md.value);
        cv.put(COLUMN_STAGE, md.stage);
        cv.put(COLUMN_DATE, md.next_time);
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.id)});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Word> selectByCurTime() {
        long curTime = System.currentTimeMillis();
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_DATE + " < ?", new String[]{String.valueOf(curTime)}, null, null, null);

        ArrayList<Word> arr = new ArrayList<Word>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                String value = mCursor.getString(NUM_COLUMN_VALUE);
                int stage = mCursor.getInt(NUM_COLUMN_STAGE);
                long date = mCursor.getLong(NUM_COLUMN_DATE);
                arr.add(new Word(id, name, value, stage, date));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    public Word select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_NAME);
        String value = mCursor.getString(NUM_COLUMN_VALUE);
        int stage = mCursor.getInt(NUM_COLUMN_STAGE);
        long date = mCursor.getLong(NUM_COLUMN_DATE);
        return new Word(id, name, value, stage, date);
    }

    public ArrayList<Word> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Word> arr = new ArrayList<Word>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                String value = mCursor.getString(NUM_COLUMN_VALUE);
                int stage = mCursor.getInt(NUM_COLUMN_STAGE);
                long date = mCursor.getLong(NUM_COLUMN_DATE);
                arr.add(new Word(id, name, value, stage, date));
            } while (mCursor.moveToNext());
        }
        return arr;
    }


    private class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_VALUE + " TEXT, " +
                    COLUMN_STAGE + " INTEGER, " +
                    COLUMN_DATE + " LONG);";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
