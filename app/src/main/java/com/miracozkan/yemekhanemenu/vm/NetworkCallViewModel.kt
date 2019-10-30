package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
import com.miracozkan.yemekhanemenu.base.BaseViewModel
import com.miracozkan.yemekhanemenu.datalayer.repository.NetworkCallRepository
import com.miracozkan.yemekhanemenu.util.Result
import kotlinx.coroutines.launch

// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 17:13          │
//└─────────────────────────────┘

class NetworkCallViewModel(private val networkCallRepository: NetworkCallRepository) :
    BaseViewModel() {

    val resultReq by lazy { MutableLiveData<Result<String>>() }
    val lastUpdate by lazy { MutableLiveData<Int>() }
    private lateinit var saveDbResult: String

    init {
        setMenusData()
        getLastUpdateDate()
    }

    private fun getLastUpdateDate() {
        scope.launch {
            lastUpdate.postValue(networkCallRepository.getLastUpdateDate())
        }
    }

    private fun setMenusData() {
        scope.launch {
            saveDbResult = networkCallRepository.saveDataToDB()
            when (saveDbResult) {
                "Yükleniyor" -> {
                    resultReq.postValue(Result.loading())
                }
                "İşlem Tamamlandi" -> {
                    resultReq.postValue(Result.success(saveDbResult))
                }
                "İslem Sirasında Hata Olustu" -> {
                    resultReq.postValue(Result.error(saveDbResult))
                }
            }
        }
    }
}