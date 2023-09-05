package com.example.myprojectshop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PricesDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "price3.db";
    private static final int DB_VERSION = 1;
    public PricesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(PricesContract.PriceEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PricesContract.PriceEntry.DROP_COMMAND);
        onCreate(db);
    }
}
