package com.miracozkan.yemekhanemenu.datalayer.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.miracozkan.yemekhanemenu.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:00          │
//└─────────────────────────────┘

class RetrofitClient {
    companion object {
        fun getClient(): ProjectService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ProjectService::class.java)
        }
    }
}