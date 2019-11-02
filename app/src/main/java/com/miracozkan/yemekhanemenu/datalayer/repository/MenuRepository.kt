package com.miracozkan.yemekhanemenu.datalayer.repository

import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.db.LocalDataDao
import com.miracozkan.yemekhanemenu.datalayer.model.AllType


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:10          │
//└─────────────────────────────┘

class MenuRepository(
    private val date: String,
    private val localDataDao: LocalDataDao
) : BaseRepository() {

    suspend fun getAllType(): AllType {
        return localDataDao.getAllTypeDB(date.toInt())
    }
}