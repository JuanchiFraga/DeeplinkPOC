package com.juanchi.deeplinkpoc.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.juanchi.deeplinkpoc.core.viewBinding
import com.juanchi.deeplinkpoc.databinding.LoginActivityBinding
import com.juanchi.deeplinkpoc.ui.home.HomeActivity
import com.juanchi.deeplinkpoc.ui.landing.LandingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding: LoginActivityBinding by viewBinding(LoginActivityBinding::inflate)
    private var shouldGoToHome: Boolean = true

    private val observeLoginEvent = Observer { event: LoginViewModel.LoginEvent ->
        event.goHome?.let { launchHome() }
        event.goLanding?.let { launchLanding() }
        event.finish?.let { setResultAndFinish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        shouldGoToHome = intent.getBooleanExtra(GO_HOME_KEY, true)
        viewModel.loginEvent.observe(this, observeLoginEvent)
        initView()
        interceptOnBackPress()
    }

    private fun initView() {
        binding.loginButton.setOnClickListener {
            viewModel.login(binding.sessionTime.text.toString().toLongOrNull() ?: 0L, shouldGoToHome)
        }
        binding.unLinkButton.setOnClickListener { viewModel.unLinkAccount() }
    }

    private fun interceptOnBackPress() {
        onBackPressedDispatcher.addCallback {
            if (shouldGoToHome.not()) {
                setResult(Activity.RESULT_CANCELED)
                finishAffinity()
            } else {
                remove()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setResultAndFinish() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun launchHome() {
        launchAndFinish(HomeActivity.newIntent(this))
    }

    private fun launchLanding() {
        launchAndFinish(LandingActivity.newIntent(this))
    }

    private fun launchAndFinish(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    companion object {

        private const val GO_HOME_KEY = "GO_HOME_KEY"

        fun newIntent(
            context: Context,
            shouldGoToHome: Boolean = false
        ) = Intent(context, LoginActivity::class.java).apply {
            putExtra(GO_HOME_KEY, shouldGoToHome)
        }
    }
}