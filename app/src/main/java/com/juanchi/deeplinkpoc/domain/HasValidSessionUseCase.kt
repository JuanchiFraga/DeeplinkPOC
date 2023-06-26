package com.juanchi.deeplinkpoc.domain

import com.juanchi.deeplinkpoc.data.SessionRepository
import javax.inject.Inject

class HasValidSessionUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.isSessionStarted()
    }
}