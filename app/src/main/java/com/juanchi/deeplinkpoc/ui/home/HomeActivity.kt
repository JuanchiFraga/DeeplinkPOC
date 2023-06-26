package com.juanchi.deeplinkpoc.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.juanchi.deeplinkpoc.core.AuthActivity
import com.juanchi.deeplinkpoc.core.viewBinding
import com.juanchi.deeplinkpoc.databinding.HomeActivityBinding
import com.juanchi.deeplinkpoc.ui.product.ProductActivity

class HomeActivity : AuthActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private val binding: HomeActivityBinding by viewBinding(HomeActivityBinding::inflate)

    private val homeEvent = Observer { event: HomeViewModel.HomeEvent ->
        event.showLoading?.let { binding.viewSwitcher.displayedChild = LOADING_INDEX }
        event.showHome?.let { binding.viewSwitcher.displayedChild = HOME_INDEX }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.homeEvent.observe(this, homeEvent)
        initView()
    }

    private fun initView() {
        binding.viewSwitcher.displayedChild = LOADING_INDEX
        binding.productButton.setOnClickListener {
            startActivity(ProductActivity.newIntent(this))
        }
    }

    override fun onSessionReady(intent: Intent) {
        super.onSessionReady(intent)
        viewModel.fetchHome()
    }

    companion object {

        private const val HOME_INDEX = 0
        private const val LOADING_INDEX = 1

        fun newIntent(context: Context) =  Intent(context, HomeActivity::class.java)
    }
}