package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miracozkan.yemekhanemenu.base.BaseViewModel
import com.miracozkan.yemekhanemenu.datalayer.model.AllType
import com.miracozkan.yemekhanemenu.datalayer.repository.NetworkCallRepository
import com.miracozkan.yemekhanemenu.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    // I suggest you to use this job logic so you can avoid from unnecessary requests.
    // It is really useful actions like clicking buttons.
    // Doesn't matter how many times user clicked button if Job is active.
    // It will return and does nothing.
    private var setMenusDataJob: Job? = null
    private var getLastUpdateJob: Job? = null
    private var getLastMenuJob: Job? = null

    val resultReq by lazy { MutableLiveData<Result<String>>() }
    val lastUpdate by lazy { MutableLiveData<Int>() }
    val lastMenu by lazy { MutableLiveData<Result<AllType>>() }
    private lateinit var saveDbResult: String

    init {
        getLastUpdateDate()
        getLastMenu()
        setMenusData()
    }

    private fun getLastUpdateDate() {
        if (getLastUpdateJob?.isActive == true) {
            return
        }
        getLastUpdateJob = launchGetLastUpdateDate()
    }

    private fun launchGetLastUpdateDate(): Job? {
        return viewModelScope.launch(Dispatchers.IO) {
            lastUpdate.postValue(networkCallRepository.getLastUpdateDate())
        }
    }

    private fun getLastMenu() {
        if (getLastMenuJob?.isActive == true) {
            return
        }
        getLastMenuJob = launchLastMenu()
    }

    private fun launchLastMenu(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            lastMenu.postValue(Result.loading())
            val result = networkCallRepository.getLastMenu()
            if (result != null) {
                lastMenu.postValue(Result.success(result))
            } else {
                lastMenu.postValue(Result.error("Database Error!!!"))
            }
        }
    }

    private fun setMenusData() {
        if (setMenusDataJob?.isActive == true) {
            return
        }
        setMenusDataJob = launchSetMenusData()
    }

    private fun launchSetMenusData(): Job? {
        return viewModelScope.launch(Dispatchers.IO) {
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