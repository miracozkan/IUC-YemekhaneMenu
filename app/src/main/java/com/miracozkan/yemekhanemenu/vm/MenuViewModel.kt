package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
import com.miracozkan.yemekhanemenu.base.BaseViewModel
import com.miracozkan.yemekhanemenu.datalayer.model.BaseResponse
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository
import com.miracozkan.yemekhanemenu.util.Result
import com.miracozkan.yemekhanemenu.util.Status
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

    val menuList by lazy { MutableLiveData<Result<BaseResponse>>() }

    init {
        getData()
    }

    private fun getData() {
        scope.launch {
            when (menuRepository.getMenuFromRemote().status) {
                Status.ERROR -> {
                    //TODO Error Coduna göre filtrelenebilir
                    menuList.postValue(Result.error(menuRepository.getMenuFromRemote().message!!))
                }
                Status.LOADING -> {
                    menuList.postValue(Result.loading())
                }
                Status.SUCCESS -> {
                    menuList.postValue(Result.success(menuRepository.getMenuFromRemote().data!!))
                }
            }
        }
    }
}