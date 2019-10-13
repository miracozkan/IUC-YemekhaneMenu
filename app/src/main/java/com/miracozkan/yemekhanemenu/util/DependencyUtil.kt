package com.miracozkan.yemekhanemenu.util

import com.miracozkan.yemekhanemenu.datalayer.remote.ProjectService
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:26          │
//└─────────────────────────────┘

object DependencyUtil {
    fun getMenuRepository(projectService: ProjectService)
            : MenuRepository = MenuRepository(projectService)
}