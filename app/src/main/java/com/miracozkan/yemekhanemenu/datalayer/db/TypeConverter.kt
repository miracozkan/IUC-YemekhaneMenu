package com.miracozkan.yemekhanemenu.datalayer.db

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miracozkan.yemekhanemenu.datalayer.model.*


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 18:22          │
//└─────────────────────────────┘

object TypeConverter {

    @androidx.room.TypeConverter
    @JvmStatic
    fun kahvaltiToString(array: List<Kahvalti>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }


    @androidx.room.TypeConverter
    @JvmStatic
    fun ogleToString(array: List<Ogle>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun aksamToString(array: List<Aksam>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun diyetToString(array: List<Diyet>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun veganToString(array: List<Vegan>): String {
        return if (array.isEmpty()) {
            ""
        } else {
            Gson().toJson(array)
        }
    }


    @androidx.room.TypeConverter
    @JvmStatic
    fun oglestring(value: String): List<Ogle> {
        val listType = object : TypeToken<List<Ogle>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun aksamstring(value: String): List<Aksam> {
        val listType = object : TypeToken<List<Aksam>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun diyetstring(value: String): List<Diyet> {
        val listType = object : TypeToken<List<Diyet>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun veganstring(value: String): List<Vegan> {
        val listType = object : TypeToken<List<Vegan>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    @JvmStatic
    fun kahvaltistring(value: String): List<Kahvalti> {
        val listType = object : TypeToken<List<Kahvalti>>() {}.type
        return Gson().fromJson(value, listType)
    }
}