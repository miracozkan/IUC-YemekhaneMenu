package com.miracozkan.yemekhanemenu.datalayer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miracozkan.yemekhanemenu.datalayer.model.Aksam
import com.miracozkan.yemekhanemenu.datalayer.model.AllType
import com.miracozkan.yemekhanemenu.datalayer.model.Diyet
import com.miracozkan.yemekhanemenu.datalayer.model.Kahvalti
import com.miracozkan.yemekhanemenu.datalayer.model.Ogle
import com.miracozkan.yemekhanemenu.datalayer.model.Vegan


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 17:08          │
//└─────────────────────────────┘

@Database(
    entities = [Kahvalti::class,
        Ogle::class, Aksam::class,
        Vegan::class, Diyet::class,
        AllType::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    abstract fun localDataDao(): LocalDataDao

    companion object {
        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getInstance(context: Context): ProjectDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "yemekhane_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}