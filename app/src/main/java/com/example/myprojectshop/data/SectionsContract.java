package com.example.myprojectshop.data;

import android.provider.BaseColumns;

public class SectionsContract {
    public static final class SectionsEntry implements BaseColumns{
        public static final String TABLE_NAME = "sections";
        public static final String COLUMN_ID = "idSec";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGE_DELETE = "imageDell";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID + " " + TYPE_TEXT + ", "
                + COLUMN_NAME + " " + TYPE_TEXT + ", "
                + COLUMN_IMAGE_DELETE + " " + TYPE_TEXT + ", "
                + COLUMN_IMAGE + " " + TYPE_TEXT + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
