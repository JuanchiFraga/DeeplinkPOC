package com.juanchi.deeplinkpoc.core

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanchi.deeplinkpoc.domain.HasValidSessionUseCase
import com.juanchi.deeplinkpoc.domain.IsAccountLinkedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val hasValidSessionUseCase: HasValidSessionUseCase,
    private val isAccountLinkedUseCase: IsAccountLinkedUseCase
) : ViewModel() {

    private val _sessionState = MutableLiveData<SessionState>()
    val sessionState: LiveData<SessionState>
        get() = _sessionState

    fun checkSession(intent: Intent?) = viewModelScope.launch {
        when {
            hasValidSessionUseCase() -> emitEvent(onSessionReady = intent)
            isAccountLinkedUseCase() -> emitEvent(showLogin = Unit)
            else -> emitEvent(showLanding = Unit)
        }
    }

    private suspend fun emitEvent(
        onSessionReady: Intent? = null,
        showLogin: Unit? = null,
        showLanding: Unit? = null
    ) = withContext(Dispatchers.Main) {
        _sessionState.value = SessionState(
            onSessionReady = onSessionReady,
            showLogin = showLogin,
            showLanding = showLanding
        )
    }

    data class SessionState(
        val onSessionReady: Intent?,
        val showLogin: Unit?,
        val showLanding: Unit?
    )
}