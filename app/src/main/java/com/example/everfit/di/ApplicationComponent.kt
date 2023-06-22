package com.example.everfit.di

import com.example.everfit.di.module.LocalModule
import com.example.everfit.di.module.NetworkModule
import com.example.everfit.di.module.ViewModelModule
import com.example.everfit.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocalModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainViewModel: MainViewModel)
}
