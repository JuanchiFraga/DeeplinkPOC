package com.juanchi.deeplinkpoc.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.juanchi.deeplinkpoc.ui.home.HomeActivity
import com.juanchi.deeplinkpoc.ui.landing.LandingActivity
import com.juanchi.deeplinkpoc.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class AuthActivity : AppCompatActivity() {

    private val sessionViewModel: AuthViewModel by viewModels()
    private var wasSessionReady: Boolean = false
    private var isContentLoaded: Boolean = false

    private val sessionObserver = Observer { state: AuthViewModel.SessionState ->
        state.onSessionReady?.let { givenSessionReady(it) }
        state.showLogin?.let { launchLogin() }
        state.showLanding?.let { launchLanding() }
    }

    private val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            sessionViewModel.checkSession(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionViewModel.sessionState.observe(this, sessionObserver)
        interceptOnBackPress()
    }

    override fun onStart() {
        super.onStart()
        wasSessionReady = false
        sessionViewModel.checkSession(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        val hasLoadedContent = isContentLoaded
        isContentLoaded = false
        if (wasSessionReady && hasLoadedContent) { // check race condition
            sessionViewModel.checkSession(intent)
        }
    }

    private fun givenSessionReady(intent: Intent) {
        wasSessionReady = true
        if (isContentLoaded.not()) {
            isContentLoaded = true
            onSessionReady(intent)
        }
    }

    open fun onSessionReady(intent: Intent) {}

    private fun interceptOnBackPress() {
        onBackPressedDispatcher.addCallback {
            if (isLastActivityInStack()) {
                startActivity(HomeActivity.newIntent(this@AuthActivity))
                finish()
            } else {
                remove()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun launchLogin() {
        loginLauncher.launch(LoginActivity.newIntent(this))
    }

    private fun launchLanding() {
        val landingIntent = LandingActivity.newIntent(this)
        landingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(landingIntent)
        finish()
    }
}