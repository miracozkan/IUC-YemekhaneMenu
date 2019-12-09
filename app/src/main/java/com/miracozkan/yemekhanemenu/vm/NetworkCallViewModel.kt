package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.LiveData
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

    private val _isLoading by lazy { MutableLiveData<Boolean>() }
    var isLoading: LiveData<Boolean> = _isLoading

    private var setMenusDataJob: Job? = null
    private var getLastUpdateJob: Job? = null
    private var getLastMenuJob: Job? = null

    val resultReq by lazy { MutableLiveData<Result<String>>() }
    private val lastUpdate by lazy { MutableLiveData<Int>() }
    val lastMenu by lazy { MutableLiveData<Result<AllType>>() }
    private lateinit var saveDbResult: String

    init {
        _isLoading.postValue(false)
        getLastUpdateDate()
        getLastMenu()
        setMenusData()
    }

    fun updateIsLoadingState(state: Boolean) {
        _isLoading.postValue(state)
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