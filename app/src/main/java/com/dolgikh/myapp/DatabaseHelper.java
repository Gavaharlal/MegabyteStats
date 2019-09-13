package com.dolgikh.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DATE = "date";
    private static final String TABLE_NAME = "Songs";

    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AUTHOR + " TEXT" + ", "
                + COLUMN_TITLE + " TEXT" + ", "
                + COLUMN_DATE + " TEXT"
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException();
    }

    void addSong(Song song) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTHOR, song.getAuthor());
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_TITLE, song.getTitle());
        values.put(COLUMN_DATE, song.getDateAdding());
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    List<Song> getSongsList() {

        List<Song> songs = new ArrayList<>();

        String selectRequest = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectRequest, null);

        if (cursor.moveToFirst()) {
            do {
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                songs.add(new Song(author, title, date));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    Song getLastSong() {
        Song result = null;

        String selectRequest = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectRequest, null);

        if (cursor.moveToLast()) {
            String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            result = new Song(author, title, date);
        }

        cursor.close();
        db.close();

        return result;
    }

}
