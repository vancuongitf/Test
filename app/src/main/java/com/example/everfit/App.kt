package com.example.everfit

import android.app.Application
import com.example.everfit.di.ApplicationComponent
import com.example.everfit.di.DaggerApplicationComponent
import com.example.everfit.di.module.LocalModule
import com.example.everfit.di.module.NetworkModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        var instance: App? = null
    }

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    open fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .networkModule(NetworkModule())
            .localModule(LocalModule(applicationContext))
            .build()
    }
}
