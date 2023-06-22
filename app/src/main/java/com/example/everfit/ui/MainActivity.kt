package com.example.everfit.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everfit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var binding: ActivityMainBinding? = null
    private var progressDialog: ProgressDialog? = null
    private val dateWorkoutAdapter = DateWorkoutAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage("Loading...")
        }
        viewModel.workouts.observe(this) {
            dateWorkoutAdapter.items.clear()
            dateWorkoutAdapter.items.addAll(it)
            dateWorkoutAdapter.notifyDataSetChanged()
        }
        viewModel.loadingState.observe(this) {
            if (it) {
                progressDialog?.show()
            } else {
                progressDialog?.dismiss()
            }
        }
        viewModel.getCachedData()
        viewModel.getWorkouts()
        binding?.recyclerView?.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity)
            adapter = dateWorkoutAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
