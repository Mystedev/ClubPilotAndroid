package com.example.clubpilot.SQLite;

import android.provider.BaseColumns;

public final class NewsContract {
    private NewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITOL = "titol";
        public static final String COLUMN_DESCRIPCIO = "descripcio";
        public static final String COLUMN_AUTOR = "autor";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_IMATGE = "imatge";
    }
}
