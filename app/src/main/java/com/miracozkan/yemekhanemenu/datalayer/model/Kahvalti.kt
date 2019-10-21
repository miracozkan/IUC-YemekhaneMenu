package com.miracozkan.yemekhanemenu.datalayer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 07.10.2019 - 15:50          │
//└─────────────────────────────┘

@Parcelize
@Entity(tableName = "Kahvalti")
data class Kahvalti(
    @PrimaryKey(autoGenerate = true) val _id: Int? = null,
    @SerializedName("tarih") val tarih: String? = null,
    @SerializedName("menu") val menu: String? = null
) : Parcelable
