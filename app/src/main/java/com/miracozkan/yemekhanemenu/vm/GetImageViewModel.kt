package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miracozkan.yemekhanemenu.base.BaseViewModel
import com.miracozkan.yemekhanemenu.datalayer.repository.GetImageRepository
import kotlinx.coroutines.launch


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 14.10.2019 - 20:56          │
//└─────────────────────────────┘

class GetImageViewModel(private val getImageRepository: GetImageRepository) : BaseViewModel() {

    val imageLinks by lazy { MutableLiveData<List<String>>() }

    init {
        // In big projects, usually it is better to call functions manually instead of
        // calling them in init scope. It will be easier to refactor and do changes.
        getLinks()
    }

    fun getImageList() {
        viewModelScope.launch {
            getImageRepository.getLink()
        }
    }

    private fun getLinks() {
        viewModelScope.launch {
            imageLinks.postValue(getImageRepository.links)
        }
    }
}