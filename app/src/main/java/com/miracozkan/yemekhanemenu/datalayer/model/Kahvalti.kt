package com.miracozkan.yemekhanemenu.datalayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 07.10.2019 - 15:50          │
//└─────────────────────────────┘


@Entity(tableName = "Kahvalti")
data class Kahvalti(
    @PrimaryKey(autoGenerate = true)
    val _id: Int? = null
)
