package com.example.myprojectshop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myprojectshop.lists.Section;

import java.util.List;
@Dao
public interface SectionDao {
    @Query("SELECT * FROM sections23")
    List<Section> getAllSection();
    @Query("DELETE FROM sections23")
    void deleteAllSection();
    @Insert
    void insertSection(Section section);
}
