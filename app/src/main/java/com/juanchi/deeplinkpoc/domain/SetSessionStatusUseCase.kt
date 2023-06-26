package com.juanchi.deeplinkpoc.domain

import com.juanchi.deeplinkpoc.data.SessionRepository
import javax.inject.Inject

class SetSessionStatusUseCase @Inject constructor(
    private val repository: SessionRepository
){

    suspend operator fun invoke(sessionStatus: Boolean) {
        repository.setSessionStarted(sessionStatus)
    }
}