package com.miracozkan.yemekhanemenu.datalayer.repository

import com.miracozkan.yemekhanemenu.BuildConfig
import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.db.ProjectDao
import com.miracozkan.yemekhanemenu.datalayer.model.AllType
import com.miracozkan.yemekhanemenu.datalayer.model.BaseResponse
import com.miracozkan.yemekhanemenu.datalayer.remote.ProjectService
import com.miracozkan.yemekhanemenu.util.Result
import com.miracozkan.yemekhanemenu.util.Status


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 17:13          │
//└─────────────────────────────┘

class NetworkCallRepository(
    private val date: Int,
    private val projectService: ProjectService,
    private val projectDao: ProjectDao
) : BaseRepository() {

    private suspend fun getMenuFromRemote(): Result<BaseResponse> {
        return getResult {
            projectService.getMenuAsync(BuildConfig.API_KEY)
        }
    }

    suspend fun saveDataToDB(): String {
        return when (getMenuFromRemote().status) {
            Status.LOADING -> {
                "Yükleniyor"
            }
            Status.SUCCESS -> {
                getMenuFromRemote().data?.let { _response ->
                    projectDao.saveAll(
                        AllType(
                            _id = _response.date,
                            kahvalti = _response.kahvalti,
                            aksam = _response.aksam,
                            diyet = _response.diyet,
                            ogle = _response.ogle,
                            vegan = _response.vegan
                        )
                    )
                }
                "İşlem Tamamlandi"
            }
            Status.ERROR -> {
                "İslem Sirasında Hata Olustu"
            }
        }
    }

    suspend fun getLastUpdateDate(): Int {
        return projectDao.getLastDate(date)
    }

    suspend fun getLastMenu(): AllType? {
        return projectDao.getLastMenu(date)
    }
}