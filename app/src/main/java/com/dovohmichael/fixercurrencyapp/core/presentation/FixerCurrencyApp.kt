package com.dovohmichael.fixercurrencyapp.core.presentation

import android.app.Application
import com.dovohmichael.fixercurrencyapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FixerCurrencyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}