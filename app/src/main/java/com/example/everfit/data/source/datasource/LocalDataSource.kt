package com.example.everfit.data.source.datasource

import com.example.everfit.data.model.WorkoutsResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun saveWorkouts(workoutsResponse: WorkoutsResponse)

    fun getWorkouts(): Flow<WorkoutsResponse>
}
