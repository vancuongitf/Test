package com.example.everfit.data.source

import android.content.SharedPreferences
import com.example.everfit.data.model.WorkoutsResponse
import com.example.everfit.data.source.datasource.LocalDataSource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalRepository(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalDataSource {
    companion object {
        private const val KEY_WORKOUT_RESPONSE = "KEY_WORKOUT_RESPONSE"
    }

    override fun saveWorkouts(workoutsResponse: WorkoutsResponse) {
        sharedPreferences.edit().putString(KEY_WORKOUT_RESPONSE, gson.toJson(workoutsResponse))
            .apply()
    }

    override fun getWorkouts(): Flow<WorkoutsResponse> {
        return flow {
            emit(
                gson.fromJson(
                    sharedPreferences.getString(KEY_WORKOUT_RESPONSE, """{"data":[]}"""),
                    WorkoutsResponse::class.java
                )
            )
        }
    }
}
