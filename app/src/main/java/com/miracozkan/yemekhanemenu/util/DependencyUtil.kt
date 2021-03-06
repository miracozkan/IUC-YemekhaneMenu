package com.miracozkan.yemekhanemenu.util

import com.miracozkan.yemekhanemenu.datalayer.db.LocalDataDao
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDao
import com.miracozkan.yemekhanemenu.datalayer.remote.ProjectService
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository
import com.miracozkan.yemekhanemenu.datalayer.repository.NetworkCallRepository


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:26          │
//└─────────────────────────────┘

object DependencyUtil {
    fun getMenuRepository(date: String, localDataDao: LocalDataDao)
            : MenuRepository = MenuRepository(date, localDataDao)

    fun getNetworkCallRepository(
        date: Int,
        projectService: ProjectService,
        projectDao: ProjectDao
    ): NetworkCallRepository = NetworkCallRepository(date, projectService, projectDao)

}