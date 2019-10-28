package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.MutableLiveData
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
        getLinks()
    }

    fun getImageList() {
        scope.launch {
            getImageRepository.getLink()
        }
    }

    private fun getLinks() {
        scope.launch {
            imageLinks.postValue(getImageRepository.links)
        }
    }
}