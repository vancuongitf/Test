package com.example.everfit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutsResponse(
    internal val data: List<DateWorkout>
) : Parcelable
