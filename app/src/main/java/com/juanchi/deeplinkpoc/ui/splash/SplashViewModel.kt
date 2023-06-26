package com.juanchi.deeplinkpoc.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanchi.deeplinkpoc.domain.IsAccountLinkedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isAccountLinkedUseCase: IsAccountLinkedUseCase
): ViewModel() {

    private val _sessionState = MutableLiveData<SplashEvent>()
    val sessionState: LiveData<SplashEvent>
        get() = _sessionState

    fun checkStatusAndRoute() = viewModelScope.launch {
        when {
            isAccountLinkedUseCase() -> emitEvent(goLogin = Unit)
            else -> emitEvent(goLanding = Unit)
        }
    }

    private suspend fun emitEvent(
        goLogin: Unit? = null,
        goLanding: Unit? = null
    ) = withContext(Dispatchers.Main) {
        _sessionState.value = SplashEvent(
            goLogin = goLogin,
            goLanding = goLanding
        )
    }

    data class SplashEvent(
        val goLogin: Unit?,
        val goLanding: Unit?
    )
}