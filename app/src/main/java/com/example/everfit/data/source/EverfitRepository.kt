package com.example.everfit.data.source

import com.example.everfit.data.source.datasource.EverfitDataSource
import com.example.everfit.data.source.remote.EverfitRemoteDataSource

class EverfitRepository(private val everfitRemoteDataSource: EverfitRemoteDataSource) :
    EverfitDataSource {

    override fun getWorkouts() = everfitRemoteDataSource.getWorkouts()
}
