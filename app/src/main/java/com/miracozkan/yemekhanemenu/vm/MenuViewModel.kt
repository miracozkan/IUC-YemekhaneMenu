package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
import com.miracozkan.yemekhanemenu.base.BaseViewModel
import com.miracozkan.yemekhanemenu.datalayer.model.AllType
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository
import com.miracozkan.yemekhanemenu.util.Result
import kotlinx.coroutines.launch


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:09          │
//└─────────────────────────────┘

class MenuViewModel(private val menuRepository: MenuRepository) : BaseViewModel() {

    val allType by lazy { MutableLiveData<Result<AllType>>() }

    init {
        getAllType()
    }

    private fun getAllType() {
        scope.launch {
            allType.postValue(Result.loading())
            if (menuRepository.getAllType() == null) {
                allType.postValue(Result.error("Liste Bos"))
            } else {
                allType.postValue(Result.success(menuRepository.getAllType()!!))
            }
        }
    }

}