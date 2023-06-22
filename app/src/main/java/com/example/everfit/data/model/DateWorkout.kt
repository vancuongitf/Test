package com.example.everfit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateWorkout(
    @SerializedName("_id") internal val id: String,
    internal val assignments: List<Workout>
) : Parcelable {
    enum class DateIndex {
        PAST, TODAY, FUTURE
    }
}
