package com.miracozkan.yemekhanemenu.datalayer.db

import androidx.room.Dao
import androidx.room.Query
import com.miracozkan.yemekhanemenu.datalayer.model.*


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 17:37          │
//└─────────────────────────────┘

@Dao
interface LocalDataDao {

    @androidx.room.Query("SELECT * FROM Kahvalti")
    suspend fun getKahvaltiMenuFromDB(): List<Kahvalti>

    @androidx.room.Query("SELECT * FROM Ogle")
    suspend fun getOgleMenuFromDB(): List<Ogle>

    @androidx.room.Query("SELECT * FROM Aksam")
    suspend fun getAksamMenuFromDB(): List<Aksam>

    @androidx.room.Query("SELECT * FROM Diyet")
    suspend fun getDiyetMenuFromDB(): List<Diyet>

    @androidx.room.Query("SELECT * FROM Vegan")
    suspend fun getVeganMenuFromDB(): List<Vegan>

    @Query("SELECT * FROM AllType WHERE _id = :date")
    suspend fun getAllTypeDB(date: Int): AllType
}