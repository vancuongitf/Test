package com.example.everfit.data.source.remote.network

import com.example.everfit.data.model.WorkoutsResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("/workouts")
    suspend fun getWorkouts(): Response<WorkoutsResponse>
}
