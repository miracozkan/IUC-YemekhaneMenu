package com.miracozkan.yemekhanemenu.datalayer.repository

import com.miracozkan.yemekhanemenu.BuildConfig
import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.model.BaseResponse
import com.miracozkan.yemekhanemenu.datalayer.remote.ProjectService
import com.miracozkan.yemekhanemenu.util.Result


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:10          │
//└─────────────────────────────┘

class MenuRepository(private val projectService: ProjectService) : BaseRepository() {

    //TODO API_KEY ve API_URL NDK ile saklanıp çekilmeli

    suspend fun getMenuFromRemote(): Result<BaseResponse> {
        return getResult {
            projectService.getMenuAsync(BuildConfig.API_KEY)
        }
    }
}