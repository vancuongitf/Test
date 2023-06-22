package com.example.everfit.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.everfit.BuildConfig
import com.example.everfit.data.source.LocalRepository
import com.example.everfit.data.source.datasource.LocalDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalModule() {

    lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    @Provides
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesLocalRepository(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): LocalDataSource {
        return LocalRepository(sharedPreferences, gson)
    }
}
