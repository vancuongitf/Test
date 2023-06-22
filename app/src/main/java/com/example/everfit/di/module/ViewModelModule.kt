package com.example.everfit.di.module

import com.example.everfit.ui.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
    ): MainViewModel {
        return MainViewModel()
    }
}
