package com.miracozkan.yemekhanemenu.datalayer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miracozkan.yemekhanemenu.datalayer.model.FoodImage


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 15.10.2019 - 21:02          │
//└─────────────────────────────┘

@Dao
interface FoodImageDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFoodImage(foodImage: FoodImage)

    @Query("SELECT * FROM FoodImage WHERE name = :name ")
    fun getImageFood(name: String): FoodImage

}