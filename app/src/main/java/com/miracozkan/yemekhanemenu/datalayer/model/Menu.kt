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


data class Menu (

	@SerializedName("title") val title : String,
	@SerializedName("type") val type : String
)