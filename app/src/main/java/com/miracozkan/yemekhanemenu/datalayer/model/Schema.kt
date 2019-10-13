package com.miracozkan.yemekhanemenu.datalayer.model

import com.google.gson.annotations.SerializedName
import com.miracozkan.yemekhanemenu.datalayer.model.Properties

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 07.10.2019 - 15:50          │
//└─────────────────────────────┘


data class Schema (

	@SerializedName("type") val type : String,
	@SerializedName("properties") val properties : Properties
)