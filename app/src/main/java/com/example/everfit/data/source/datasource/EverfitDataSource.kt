package com.example.everfit.data.source.datasource

import com.example.everfit.data.model.WorkoutsResponse
import com.example.everfit.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface EverfitDataSource {

    fun getWorkouts(): Flow<ApiResponse<WorkoutsResponse>>
}
