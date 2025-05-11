package com.example.clubpilot.SQLite;
// Archivo: DatabaseHelper.java

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clubpilot.Fan.NewsData;
import com.example.clubpilot.Player.Event;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "club.db";
    private static final int DATABASE_VERSION = 1;

    // SQL per crear taula  events
    private static final String SQL_CREATE_EVENTS =
            "CREATE TABLE " + EventContract.EventEntry.TABLE_NAME + " (" +
                    EventContract.EventEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    EventContract.EventEntry.COLUMN_CATEGORIA + " TEXT," +
                    EventContract.EventEntry.COLUMN_DATA + " TEXT," +
                    EventContract.EventEntry.COLUMN_NOM + " TEXT," +
                    EventContract.EventEntry.COLUMN_TITOL + " TEXT," +
                    EventContract.EventEntry.COLUMN_IMATGE + " TEXT)";

    // SQL per crear taula noticies
    private static final String SQL_CREATE_NEWS =
            "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
                    NewsContract.NewsEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    NewsContract.NewsEntry.COLUMN_TITOL + " TEXT," +
                    NewsContract.NewsEntry.COLUMN_DESCRIPCIO + " TEXT," +
                    NewsContract.NewsEntry.COLUMN_AUTOR + " TEXT," +
                    NewsContract.NewsEntry.COLUMN_DATA + " TEXT," +
                    NewsContract.NewsEntry.COLUMN_IMATGE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EVENTS);
        db.execSQL(SQL_CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventContract.EventEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(db);
    }

    // Metode per afegir events
    public void insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventContract.EventEntry.COLUMN_ID, event.getId());
        values.put(EventContract.EventEntry.COLUMN_CATEGORIA, event.getDescription());
        values.put(EventContract.EventEntry.COLUMN_DATA, event.getDate());
        values.put(EventContract.EventEntry.COLUMN_NOM, event.getNom());
        values.put(EventContract.EventEntry.COLUMN_TITOL, event.getTitol());
        values.put(EventContract.EventEntry.COLUMN_IMATGE, event.getImatge());
        db.insert(EventContract.EventEntry.TABLE_NAME, null, values);
    }

    // Metode per afegir noticies
    public void insertNews(NewsData news) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NewsContract.NewsEntry.COLUMN_ID, news.getId());
        values.put(NewsContract.NewsEntry.COLUMN_TITOL, news.getTitol());
        values.put(NewsContract.NewsEntry.COLUMN_DESCRIPCIO, news.getDescripcio());
        values.put(NewsContract.NewsEntry.COLUMN_AUTOR, news.getAutor());
        values.put(NewsContract.NewsEntry.COLUMN_DATA, news.getData());
        values.put(NewsContract.NewsEntry.COLUMN_IMATGE, news.getImatge());

        db.insert(NewsContract.NewsEntry.TABLE_NAME, null, values);
    }

    // Metode per esborrar totes les taules
    public void clearTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EventContract.EventEntry.TABLE_NAME, null, null);
        db.delete(NewsContract.NewsEntry.TABLE_NAME, null, null);
    }
}