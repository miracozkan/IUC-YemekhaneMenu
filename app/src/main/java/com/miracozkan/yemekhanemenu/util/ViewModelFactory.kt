package com.miracozkan.yemekhanemenu.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository
import com.miracozkan.yemekhanemenu.vm.MenuViewModel


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:09          │
//└─────────────────────────────┘

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MenuViewModel::class.java) -> {
                MenuViewModel(this.repository as MenuRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
