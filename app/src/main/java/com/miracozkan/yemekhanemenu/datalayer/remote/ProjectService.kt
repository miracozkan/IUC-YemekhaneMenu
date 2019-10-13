package com.miracozkan.yemekhanemenu.datalayer.remote

import com.miracozkan.yemekhanemenu.datalayer.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:00          │
//└─────────────────────────────┘

interface ProjectService {
    @GET("mirac.php")
    suspend fun getMenuAsync(
        @Query("tokenKey") siteKey: String
    ): Response<BaseResponse>
}