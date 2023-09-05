package com.example.myprojectshop;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myprojectshop.lists.Section;

@Database(entities = {Section.class}, version = 1, exportSchema = false)
public abstract class SectionsDatabase extends RoomDatabase {
    private static SectionsDatabase database;
    private static final String DB_NAME = "section23.db";
    private static Object LOCK = new Object();
    public static SectionsDatabase getInstance(Context context){
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, SectionsDatabase.class, DB_NAME).allowMainThreadQueries().build();
            }
        }
        return database;
    }
    public abstract SectionDao sectionDao();
}