package com.miracozkan.yemekhanemenu.datalayer.model

import android.os.Parcelable
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
data class Aksam(

    @SerializedName("tarih") val tarih: String? = null,
    @SerializedName("menu") val menu: String? = null
) : Parcelable