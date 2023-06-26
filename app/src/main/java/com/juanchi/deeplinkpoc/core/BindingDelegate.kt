package com.juanchi.deeplinkpoc.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline inflater: (LayoutInflater) -> T
): ReadOnlyProperty<AppCompatActivity, T> {
    return object : ReadOnlyProperty<AppCompatActivity, T> {

        private var binding: T? = null

        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            binding?.let { return it }
            binding = inflater.invoke(thisRef.layoutInflater)
            thisRef.lifecycle.addObserver(ViewBindingLifecycleObserver { binding = null })
            return binding!!
        }
    }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    crossinline inflater: (LayoutInflater, ViewGroup?, Boolean) -> T
): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {

        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            binding?.let { return it }
            val viewGroup: ViewGroup? = thisRef.view?.parent as? ViewGroup
            binding = inflater.invoke(layoutInflater, viewGroup, false)
            thisRef.viewLifecycleOwner.lifecycle.addObserver(ViewBindingLifecycleObserver { binding = null })
            return binding!!
        }
    }
}

class ViewBindingLifecycleObserver(private val onDestroyAction: () -> Unit) : DefaultLifecycleObserver {
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        onDestroyAction.invoke()
    }
}