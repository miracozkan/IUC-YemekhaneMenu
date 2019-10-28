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
//│ 14.10.2019 - 18:10          │
//└─────────────────────────────┘

@Parcelize
@Entity(tableName = "AllType")
data class AllType(
    @PrimaryKey val _id: Int? = null,
    @SerializedName("aksam")
    val aksam: List<Aksam>? = null,
    @SerializedName("ogle")
    val ogle: List<Ogle>? = null,
    @SerializedName("diyet")
    val diyet: List<Diyet>? = null,
    @SerializedName("vegan")
    val vegan: List<Vegan>? = null,
    @SerializedName("kahvalti")
    val kahvalti: List<Kahvalti>? = null
) : Parcelable