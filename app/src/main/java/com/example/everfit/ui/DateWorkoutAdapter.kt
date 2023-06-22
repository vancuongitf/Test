package com.example.everfit.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.everfit.data.model.DateWorkout
import com.example.everfit.databinding.ItemDateWorkoutBinding
import com.example.everfit.extensions.getDayIndex
import java.text.SimpleDateFormat
import java.util.Calendar

class DateWorkoutAdapter(internal val items: MutableList<DateWorkout> = mutableListOf()) :
    RecyclerView.Adapter<DateWorkoutAdapter.DateWorkoutHolder>() {

    private var selectedWorkout: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateWorkoutHolder {
        return DateWorkoutHolder(
            ItemDateWorkoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = 7

    override fun onBindViewHolder(holder: DateWorkoutHolder, position: Int) {
        holder.onBind(position, items.getOrNull(position))
    }

    inner class DateWorkoutHolder(private val binding: ItemDateWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun onBind(position: Int, item: DateWorkout?) {
            val cal: Calendar = Calendar.getInstance()
            val todayTimestamp = cal.timeInMillis
            cal.apply {
                set(Calendar.DAY_OF_WEEK, firstDayOfWeek + 1)
                add(Calendar.DAY_OF_WEEK, position)
            }
            val dayIndex = cal.getDayIndex(todayTimestamp)
            binding.tvDate.text = cal.get(Calendar.DAY_OF_MONTH).toString()
            val simpleDateFormat = SimpleDateFormat("EEE", java.util.Locale.ENGLISH)
            binding.tvDateName.text = simpleDateFormat.format(cal.time).toUpperCase()
            binding.tvDate.isActivated = dayIndex == DateWorkout.DateIndex.TODAY
            binding.tvDateName.isActivated = dayIndex == DateWorkout.DateIndex.TODAY
            binding.recyclerViewWorkouts.invalidate()
            binding.recyclerViewWorkouts.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = WorkoutAdapter(
                    item?.assignments?.filter { it.id?.isNotBlank() ?: false } ?: listOf(),
                    selectedWorkout,
                    dayIndex
                ).apply {
                    onItemClicked = {
                        selectedWorkout = it
                        this@DateWorkoutAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
