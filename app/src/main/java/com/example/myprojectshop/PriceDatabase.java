package com.example.myprojectshop;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myprojectshop.lists.Price;


@Database(entities = {Price.class}, version = 1, exportSchema = false)
public abstract class PriceDatabase extends RoomDatabase {
    private static PriceDatabase database;
    private static final String DB_NAME = "price23.db";
    private static Object LOCK = new Object();
    public static PriceDatabase getInstance(Context context){
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, PriceDatabase.class, DB_NAME).allowMainThreadQueries().build();
            }
        }
        return database;
    }
    public abstract PriceDao priceDao();
}
