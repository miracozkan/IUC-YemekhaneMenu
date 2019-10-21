package com.miracozkan.yemekhanemenu.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miracozkan.yemekhanemenu.base.BaseRepository
import com.miracozkan.yemekhanemenu.datalayer.repository.GetImageRepository
import com.miracozkan.yemekhanemenu.datalayer.repository.MenuRepository
import com.miracozkan.yemekhanemenu.datalayer.repository.NetworkCallRepository
import com.miracozkan.yemekhanemenu.vm.GetImageViewModel
import com.miracozkan.yemekhanemenu.vm.MenuViewModel
import com.miracozkan.yemekhanemenu.vm.NetworkCallViewModel


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
            modelClass.isAssignableFrom(NetworkCallViewModel::class.java) -> {
                NetworkCallViewModel(this.repository as NetworkCallRepository) as T
            }
            modelClass.isAssignableFrom(GetImageViewModel::class.java) -> {
                GetImageViewModel(this.repository as GetImageRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
