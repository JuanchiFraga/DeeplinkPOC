package com.juanchi.deeplinkpoc.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.juanchi.deeplinkpoc.core.AuthActivity
import com.juanchi.deeplinkpoc.core.viewBinding
import com.juanchi.deeplinkpoc.databinding.ProductLayoutBinding

class ProductActivity : AuthActivity() {

    private val binding: ProductLayoutBinding by viewBinding(ProductLayoutBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.noDeeplinkButton.setOnClickListener {
            startActivity(ProductDetailActivity.newIntent(it.context))
        }
    }

    override fun onSessionReady(intent: Intent) {
        super.onSessionReady(intent)
        intent.data?.getQueryParameter(PRODUCT_NAME)?.let { name ->
            binding.titleTextView.text = name
        }
    }

    companion object {

        private const val PRODUCT_NAME = "name"

        fun newIntent(context: Context) =  Intent(context, ProductActivity::class.java)
    }
}