package com.miracozkan.yemekhanemenu.base

import androidx.lifecycle.ViewModel


// Code with ❤
//┌─────────────────────────────┐
//│ Created by Mirac OZKAN      │
//│ ─────────────────────────── │
//│ mirac.ozkan123@gmail.com    │
//│ ─────────────────────────── │
//│ 05.10.2019 - 19:07          │
//└─────────────────────────────┘

abstract class BaseViewModel : ViewModel()
// I removed job logic. They've updated view model.
// viewModelScope ( which I use in view models) handles automatically what we do here.