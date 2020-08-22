package com.gutotech.dogs.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object {
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null

        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper {
            return instance ?: synchronized(LOCK) {
                prefs = PreferenceManager.getDefaultSharedPreferences(context)
                return SharedPreferencesHelper()
            }
        }
    }

    fun updateTime(time: Long) {
        prefs?.edit()?.apply {
            putLong("lastTime", time)
            apply()
        }
    }

    fun getTime() = prefs?.getLong("lastTime", 0)
}