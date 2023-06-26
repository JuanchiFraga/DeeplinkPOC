package com.juanchi.deeplinkpoc.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    suspend fun isAccountLinked(): Boolean {
        return sharedPreferences.getBoolean(ACCOUNT_LINK_KEY, false)
    }

    suspend fun setAccountLinked(isAccountLinked: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(ACCOUNT_LINK_KEY, isAccountLinked)
        }
    }

    suspend fun isSessionStarted(): Boolean {
        return sharedPreferences.getBoolean(SESSION_KEY, false)
    }

    suspend fun setSessionStarted(isSessionStarted: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(SESSION_KEY, isSessionStarted)
        }
    }

    suspend fun setSessionTimeInMillis(keepSessionTime: Long) {
        sharedPreferences.edit(commit = true) {
            putLong(SESSION_TIME_KEY, keepSessionTime)
        }
    }

    suspend fun getSessionTimeInMillis(): Long {
        return sharedPreferences.getLong(SESSION_TIME_KEY, 0L)
    }

    companion object {
        private const val SESSION_TIME_KEY = "SESSION_TIME_KEY"
        private const val SESSION_KEY = "SESSION_KEY"
        private const val ACCOUNT_LINK_KEY = "ACCOUNT_LINK_KEY"
    }
}