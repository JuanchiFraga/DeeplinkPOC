package com.juanchi.deeplinkpoc.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.util.UUID

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val arguments: WorkerParameters,
    private val repository: SessionRepository
) : CoroutineWorker(context, arguments) {

    override suspend fun doWork(): Result {
        delay(repository.getSessionTimeInMillis())
        repository.setSessionStarted(false)
        return Result.success()
    }

    companion object {

        fun enqueue(applicationContext: Context): UUID {
            val logoutWorker = OneTimeWorkRequestBuilder<SessionWorker>().build()
            WorkManager.getInstance(applicationContext).enqueue(logoutWorker)
            return logoutWorker.id
        }
    }
}