package com.example.everfit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.everfit.R
import com.example.everfit.data.model.DateWorkout
import com.example.everfit.data.model.Workout
import com.example.everfit.databinding.ItemWorkoutBinding

class WorkoutAdapter(
    private val items: List<Workout>,
    private val selectedWorkout: String,
    private val dayIndex: DateWorkout.DateIndex
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    internal var onItemClicked: (id: String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder(
            ItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    inner class WorkoutViewHolder(private val binding: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked.invoke(items[adapterPosition].id ?: "")
            }
        }

        internal fun onBind(item: Workout) {
            binding.apply {
                root.isSelected = selectedWorkout == item.id
                root.isActivated = dayIndex != DateWorkout.DateIndex.FUTURE
                tvWorkoutTitle.text = item.title
                tvExercises.text =
                    item.getStatusText(
                        if (root.isSelected) root.context.getColor(R.color.white) else root.context.getColor(
                            R.color.sunset_orange
                        ), dayIndex
                    )
                imgWorkoutSelected.visibility = if (root.isSelected) View.VISIBLE else View.GONE
            }
        }
    }
}
