package com.juanchi.deeplinkpoc.core

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AuthFragment : AppCompatActivity() {

    private val sessionViewModel: AuthViewModel by viewModels()

    private val sessionObserver = Observer { state: AuthViewModel.SessionState ->
        state.onSessionReady?.let { onSessionReady(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionViewModel.sessionState.observe(this, sessionObserver)
    }

    open fun onSessionReady(intent: Intent) {}
}