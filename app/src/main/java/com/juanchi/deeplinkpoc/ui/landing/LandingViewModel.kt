package com.juanchi.deeplinkpoc.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanchi.deeplinkpoc.domain.SetAccountLinkStatusUseCase
import com.juanchi.deeplinkpoc.domain.SetSessionStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val setAccountLinkStatusUseCase: SetAccountLinkStatusUseCase,
    private val setSessionStatusUseCase: SetSessionStatusUseCase
): ViewModel() {

    private val _landingEvent = MutableLiveData<LandingEvent>()
    val landingEvent: LiveData<LandingEvent>
        get() = _landingEvent

    fun linkAccount() = viewModelScope.launch {
        setAccountLinkStatusUseCase(true)
        setSessionStatusUseCase(true)
        emitEvent(goHome = Unit)
    }

    private suspend fun emitEvent(
        goHome: Unit? = null,
    ) = withContext(Dispatchers.Main) {
        _landingEvent.value = LandingEvent(
            goHome = goHome
        )
    }

    data class LandingEvent(
        val goHome: Unit?
    )
}