package com.example.myprojectshop.data;

import android.provider.BaseColumns;

public class PricesContract {
    public static final class PriceEntry implements BaseColumns{
        public static final String TABLE_NAME = "prices";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SECTION_ID = "sectionId";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGE_DELL = "imageDEll";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_COST_SAIL = "costSail";
        public static final String COLUMN_PRICE_UNIT = "priceUnit";
        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";
        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID + " " + TYPE_TEXT + ", "
                + COLUMN_SECTION_ID + " " + TYPE_TEXT + ", "
                + COLUMN_NAME + " " + TYPE_TEXT + ", "
                + COLUMN_IMAGE + " " + TYPE_TEXT + ", "
                + COLUMN_IMAGE_DELL + " " + TYPE_TEXT + ", "
                + COLUMN_COST + " " + TYPE_TEXT + ", "
                + COLUMN_COST_SAIL + " " + TYPE_TEXT + ", "
                + COLUMN_PRICE_UNIT + " " + TYPE_TEXT + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
