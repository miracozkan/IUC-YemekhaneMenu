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

    init {
        setMenusData()
    }

    private fun setMenusData() {
        scope.launch {
            when (networkCallRepository.saveDataToDB()) {
                "Yükleniyor" -> {
                    resultReq.postValue(Result.loading())
                }
                "İşlem Tamamlandi" -> {
                    resultReq.postValue(Result.success(networkCallRepository.saveDataToDB()))
                }
                "İslem Sirasında Hata Olustu" -> {
                    resultReq.postValue(Result.error(networkCallRepository.saveDataToDB()))
                }
            }
        }
    }
}