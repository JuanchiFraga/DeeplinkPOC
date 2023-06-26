package com.juanchi.deeplinkpoc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val _homeEvent = MutableLiveData<HomeEvent>()
    val homeEvent: LiveData<HomeEvent>
        get() = _homeEvent

    fun fetchHome() = viewModelScope.launch {
        emitEvent(showLoading = Unit)
        delay(500) // fetch home from service
        emitEvent(showHome = Unit)
    }

    private suspend fun emitEvent(
        showLoading: Unit? = null,
        showHome: Unit? = null
    ) = withContext(Dispatchers.Main) {
        _homeEvent.value = HomeEvent(
            showLoading = showLoading,
            showHome = showHome
        )
    }

    data class HomeEvent(
        val showLoading: Unit?,
        val showHome: Unit?
    )
}