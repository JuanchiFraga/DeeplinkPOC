package com.juanchi.deeplinkpoc.domain

import com.juanchi.deeplinkpoc.data.SessionRepository
import javax.inject.Inject

class SetAccountLinkStatusUseCase @Inject constructor(
    private val repository: SessionRepository
){

    suspend operator fun invoke(accountLinked: Boolean) {
        repository.setAccountLinked(accountLinked)
    }
}