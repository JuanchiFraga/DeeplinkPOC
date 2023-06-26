package com.juanchi.deeplinkpoc

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import androidx.work.WorkManager
import com.juanchi.deeplinkpoc.data.SessionWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.UUID
import javax.inject.Inject

@HiltAndroidApp
class DeeplinkPocApp : Application(), DefaultLifecycleObserver, Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    private var sessionWorkerUUID: UUID? = null

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        sessionWorkerUUID?.let { WorkManager.getInstance(this).cancelWorkById(it) }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        sessionWorkerUUID = SessionWorker.enqueue(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }
}