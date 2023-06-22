package com.example.everfit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everfit.App
import com.example.everfit.data.model.DateWorkout
import com.example.everfit.data.source.datasource.EverfitDataSource
import com.example.everfit.data.source.datasource.LocalDataSource
import com.example.everfit.data.source.remote.network.subscribe
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel() : ViewModel() {

    @Inject
    lateinit var everfitRepository: EverfitDataSource

    @Inject
    lateinit var localRepository: LocalDataSource

    private val _loadingState = MutableLiveData(false)
    internal val loadingState = _loadingState as LiveData<Boolean>
    private val _workouts = MutableLiveData<List<DateWorkout>>()
    internal val workouts = _workouts as LiveData<List<DateWorkout>>

    init {
        App.instance?.appComponent?.inject(this)
    }

    internal fun getCachedData() {
        viewModelScope.launch {
            localRepository.getWorkouts().collect {
                if (_workouts.value?.isNotEmpty() != true && it.data.isNotEmpty()) {
                    _workouts.value = it.data
                }
            }
        }
    }

    internal fun getWorkouts() {
        everfitRepository.getWorkouts().subscribe(
            viewModelScope,
            onSuccess = {
                _workouts.value = it.data
                localRepository.saveWorkouts(it)
            },
            onError = {
                // TODO: do nothing
            },
            doOnSubscribe = {
                _loadingState.value = true
            },
            doOnComplete = {
                _loadingState.value = false
            },
        )
    }
}
