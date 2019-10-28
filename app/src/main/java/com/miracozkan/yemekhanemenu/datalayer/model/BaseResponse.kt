package com.miracozkan.yemekhanemenu.datalayer.model

import com.google.gson.annotations.SerializedName

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 07.10.2019 - 15:50          │
//└─────────────────────────────┘


data class BaseResponse (
    @SerializedName("kahvalti") val kahvalti : List<Kahvalti>,
    @SerializedName("ogle") val ogle : List<Ogle>,
    @SerializedName("aksam") val aksam : List<Aksam>,
    @SerializedName("kumanya") val kumanya : List<Kumanya>,
    @SerializedName("diyet") val diyet : List<Diyet>,
    @SerializedName("vegan") val vegan : List<Vegan>,
    @SerializedName("cocuk") val cocuk: List<Cocuk>,
    @SerializedName("date") val date: Int

)