package com.example.clubpilot.SQLite;


import android.provider.BaseColumns;

public final class EventContract {
    private EventContract() {}

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CATEGORIA = "categoria";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_NOM = "nom";
        public static final String COLUMN_TITOL = "titol";
        public static final String COLUMN_IMATGE = "imatge";
    }
}