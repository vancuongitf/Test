package com.example.everfit.data.model

import android.os.Parcelable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    @SerializedName("_id") internal val id: String?,
    internal val status: Int,
    internal val title: String?,
    @SerializedName("exercises_count") internal val exercisesCount: Int
) : Parcelable {

    internal fun getStatusText(missedColor: Int, dayIndex: DateWorkout.DateIndex): SpannableString {
        val text = when (dayIndex) {
            DateWorkout.DateIndex.PAST -> {
                if (status == 0) {
                    "Missed • ${getExerciseCountText()}"
                } else {
                    "Completed"
                }
            }

            DateWorkout.DateIndex.TODAY -> {
                if (status == 0) {
                    "Assigned • ${getExerciseCountText()}"
                } else {
                    "Completed"
                }
            }

            else -> {
                getExerciseCountText()
            }
        }
        val spannableString = SpannableString(text)
        if (text.startsWith("Missed")) {
            spannableString.setSpan(
                ForegroundColorSpan(missedColor),
                0,
                6,
                SpannableString.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        return spannableString
    }

    private fun getExerciseCountText(): String {
        return "$exercisesCount exercise${(if (exercisesCount > 1) "s" else "")}"
    }
}
