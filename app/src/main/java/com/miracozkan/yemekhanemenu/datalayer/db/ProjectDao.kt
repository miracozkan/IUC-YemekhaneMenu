package com.miracozkan.yemekhanemenu.datalayer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miracozkan.yemekhanemenu.datalayer.model.AllType


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 17:09          │
//└─────────────────────────────┘

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAll(allType: AllType)

    @Query("SELECT COUNT(*) FROM AllType")
    suspend fun getAllTypeCount(): Int

}