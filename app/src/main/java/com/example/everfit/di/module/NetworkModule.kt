package com.example.everfit.di.module

import com.example.everfit.BuildConfig
import com.example.everfit.data.source.EverfitRepository
import com.example.everfit.data.source.datasource.EverfitDataSource
import com.example.everfit.data.source.remote.EverfitRemoteDataSource
import com.example.everfit.data.source.remote.network.APIService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gs = GsonBuilder()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gs))
        return retrofit.build()
    }

    @Provides
    @Singleton
    fun providesAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun providesAppRemoteDataSource(apiService: APIService): EverfitRemoteDataSource {
        return EverfitRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesEverfitRepository(
        everfitRemoteDataSource: EverfitRemoteDataSource,
    ): EverfitDataSource {
        return EverfitRepository(everfitRemoteDataSource)
    }
}
