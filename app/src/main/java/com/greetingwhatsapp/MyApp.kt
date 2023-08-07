package com.greetingwhatsapp

import android.app.Application
import android.content.Context
import android.util.Log

class MyApp: Application() {
    init {
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}