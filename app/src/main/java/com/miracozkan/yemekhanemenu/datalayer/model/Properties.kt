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

data class Properties (

	@SerializedName("kahvalti") val kahvalti : Kahvalti,
	@SerializedName("ogle") val ogle : Ogle,
	@SerializedName("aksam") val aksam : Aksam,
	@SerializedName("kumanya") val kumanya : Kumanya,
	@SerializedName("diyet") val diyet : Diyet,
	@SerializedName("vegan") val vegan : Vegan,
	@SerializedName("cocuk") val cocuk : Cocuk
)