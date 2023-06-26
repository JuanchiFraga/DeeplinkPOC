package com.juanchi.deeplinkpoc.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.juanchi.deeplinkpoc.ui.landing.LandingActivity
import com.juanchi.deeplinkpoc.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private val splashEvent = Observer { event: SplashViewModel.SplashEvent ->
        event.goLanding?.let { launchLanding() }
        event.goLogin?.let { launchLogin() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        viewModel.sessionState.observe(this, splashEvent)
        viewModel.checkStatusAndRoute()
    }

    private fun launchLogin() {
        val intent = LoginActivity.newIntent(this, shouldGoToHome = true)
        launchActivityAndFinish(intent)
    }

    private fun launchLanding() {
        val intent = LandingActivity.newIntent(this)
        launchActivityAndFinish(intent)
    }

    private fun launchActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}