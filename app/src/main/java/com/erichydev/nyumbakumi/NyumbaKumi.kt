package com.erichydev.nyumbakumi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NyumbaKumi : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}