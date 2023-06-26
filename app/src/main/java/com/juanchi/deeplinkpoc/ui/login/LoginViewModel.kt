package com.juanchi.deeplinkpoc.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanchi.deeplinkpoc.domain.SetAccountLinkStatusUseCase
import com.juanchi.deeplinkpoc.domain.SetSessionStatusUseCase
import com.juanchi.deeplinkpoc.domain.UpdateSessionTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setAccountLinkStatusUseCase: SetAccountLinkStatusUseCase,
    private val setSessionStatusUseCase: SetSessionStatusUseCase,
    private val updateSessionTimeUseCase: UpdateSessionTimeUseCase
): ViewModel() {

    private val _loginEvent = MutableLiveData<LoginEvent>()
    val loginEvent: LiveData<LoginEvent>
        get() = _loginEvent

    fun unLinkAccount() = viewModelScope.launch {
        setAccountLinkStatusUseCase(false)
        emitEvent(goLanding = Unit)
    }

    fun login(time: Long, shouldGoToHome: Boolean) = viewModelScope.launch {
        setSessionStatusUseCase(true)
        updateSessionTimeUseCase(time * ONE_SEC_IN_MILLIS)
        if (shouldGoToHome) {
            emitEvent(goHome = Unit)
        } else {
            emitEvent(finish = Unit)
        }
    }

    private suspend fun emitEvent(
        goHome: Unit? = null,
        goLanding: Unit? = null,
        finish: Unit? = null
    ) = withContext(Dispatchers.Main) {
        _loginEvent.value = LoginEvent(
            goHome = goHome,
            goLanding = goLanding,
            finish = finish
        )
    }

    data class LoginEvent(
        val goHome: Unit?,
        val goLanding: Unit?,
        val finish: Unit?
    )

    companion object {
        private const val ONE_SEC_IN_MILLIS = 1_000
    }
}