package com.juanchi.deeplinkpoc.ui.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.juanchi.deeplinkpoc.core.viewBinding
import com.juanchi.deeplinkpoc.databinding.LandingActivityBinding
import com.juanchi.deeplinkpoc.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {

    private val viewModel: LandingViewModel by viewModels()
    private val binding: LandingActivityBinding by viewBinding(LandingActivityBinding::inflate)

    private val observeLandingEvents = Observer { event: LandingViewModel.LandingEvent ->
        event.goHome?.let { launchHome() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.landingEvent.observe(this, observeLandingEvents)
        initView()
    }

    private fun initView() {
        binding.linkAccountButton.setOnClickListener {
            viewModel.linkAccount()
        }
    }

    private fun launchHome() {
        startActivity(HomeActivity.newIntent(this))
        finish()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LandingActivity::class.java)
    }
}