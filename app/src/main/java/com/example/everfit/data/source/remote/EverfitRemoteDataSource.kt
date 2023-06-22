package com.example.everfit.data.source.remote

import com.example.everfit.data.source.datasource.EverfitDataSource
import com.example.everfit.data.source.remote.network.APIService
import com.example.everfit.data.source.remote.network.requestFlow

class EverfitRemoteDataSource(private val apiService: APIService) : EverfitDataSource {
    override fun getWorkouts() = requestFlow {
        apiService.getWorkouts()
    }
}
