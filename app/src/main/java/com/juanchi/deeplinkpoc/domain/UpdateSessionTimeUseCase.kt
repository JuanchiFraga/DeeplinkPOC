package com.juanchi.deeplinkpoc.domain

import com.juanchi.deeplinkpoc.data.SessionRepository
import javax.inject.Inject

class UpdateSessionTimeUseCase @Inject constructor(
    private val repository: SessionRepository
) {

    suspend operator fun invoke(keepSessionTimeInMillis: Long) {
        repository.setSessionTimeInMillis(keepSessionTimeInMillis)
    }
}