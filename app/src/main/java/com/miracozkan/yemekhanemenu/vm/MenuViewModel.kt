package com.miracozkan.yemekhanemenu.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private val _isFltActionBtnShow by lazy { MutableLiveData<Boolean>() }
    val isFltActionBtnShow: LiveData<Boolean> = _isFltActionBtnShow

    fun updateFltActionBtnState(state: Boolean) {
        _isFltActionBtnShow.postValue(state)
    }

    private val _isProgressBarShow by lazy { MutableLiveData<Boolean>() }
    val isProgressBarShow: LiveData<Boolean> = _isProgressBarShow

    fun updatePrgBarState(state: Boolean) {
        _isProgressBarShow.postValue(state)
    }

    private val _selectedFragment by lazy { MutableLiveData<Int>() }
    val selectedFragment: LiveData<Int> = _selectedFragment

    fun updateSelectedFragment(fragment: Int) {
        _selectedFragment.postValue(fragment)
    }

    private val _selectedIcon by lazy { MutableLiveData<Int>() }
    val selectedIcon: LiveData<Int> = _selectedIcon

    fun updateSelectedIcon(icon: Int) {
        _selectedIcon.postValue(icon)
    }

    private val _selectedDate by lazy { MutableLiveData<String>() }
    val selectedDate: LiveData<String> = _selectedDate

    fun updateSelectedDate(date: String) {
        _selectedDate.postValue(date)
    }

    private val _allType by lazy { MutableLiveData<Result<AllType>>() }
    val allType: LiveData<Result<AllType>> = _allType

    private fun getAllType() {
        viewModelScope.launch {
            _allType.postValue(Result.loading())
            if (menuRepository.getAllType() == null) {
                _allType.postValue(Result.error("Liste Bos"))
            } else {
                _allType.postValue(Result.success(menuRepository.getAllType()!!))
            }
        }
    }

    init {
        _isFltActionBtnShow.postValue(false)
        getAllType()
    }

}