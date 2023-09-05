package com.example.myprojectshop;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myprojectshop.lists.Price;

import java.util.List;

@Dao
public interface PriceDao {
@Query("SELECT * FROM prices23")
List<Price> getAllPrices();
@Query("DELETE FROM prices23")
void deleteAllPrice();
@Insert
void insertPrice(Price price);
}
