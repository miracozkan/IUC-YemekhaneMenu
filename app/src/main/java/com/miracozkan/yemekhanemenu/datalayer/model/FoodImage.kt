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
//│ 15.10.2019 - 21:00          │
//└─────────────────────────────┘

@Parcelize
@Entity(tableName = "FoodImage")
data class FoodImage(
    @PrimaryKey(autoGenerate = true) var _id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("link") val link: String
) : Parcelable