package com.juanchi.deeplinkpoc.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.juanchi.deeplinkpoc.core.AuthActivity
import com.juanchi.deeplinkpoc.core.viewBinding
import com.juanchi.deeplinkpoc.databinding.ProductDetailLayoutBinding
import com.juanchi.deeplinkpoc.databinding.ProductLayoutBinding

class ProductDetailActivity : AppCompatActivity() {

    private val binding: ProductDetailLayoutBinding by viewBinding(ProductDetailLayoutBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {

        fun newIntent(context: Context) =  Intent(context, ProductDetailActivity::class.java)
    }
}