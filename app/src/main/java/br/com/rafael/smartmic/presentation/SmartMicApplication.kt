package br.com.rafael.smartmic.presentation

import android.app.Application

class SmartMicApplication : Application() {

    companion object {
        private lateinit var appInstance: SmartMicApplication

        fun getInstance(): SmartMicApplication {
            if (appInstance == null) {
                throw RuntimeException("Manifest Config not setup")
            }
            return appInstance
        }
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()
    }
}