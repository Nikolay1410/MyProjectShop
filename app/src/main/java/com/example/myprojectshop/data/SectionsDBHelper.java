package com.example.myprojectshop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SectionsDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "section3.db";
    private static final int DB_VERSION = 1;
    public SectionsDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SectionsContract.SectionsEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SectionsContract.SectionsEntry.DROP_COMMAND);
        onCreate(sqLiteDatabase);
    }
}
