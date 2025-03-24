package com.example.roomwithflowdemonstration.app

import android.app.Application
import timber.log.Timber

class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}