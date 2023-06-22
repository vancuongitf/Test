package com.example.everfit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.everfit.data.model.DateWorkout
import com.example.everfit.data.model.Workout
import com.example.everfit.data.model.WorkoutsResponse
import com.example.everfit.data.source.datasource.EverfitDataSource
import com.example.everfit.data.source.datasource.LocalDataSource
import com.example.everfit.data.source.remote.network.ApiResponse
import com.example.everfit.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainViewModelTest {
    @Mock
    lateinit var everfitRepository: EverfitDataSource

    @Mock
    lateinit var localRepository: LocalDataSource

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private val dataTest = WorkoutsResponse(
        listOf(
            DateWorkout(
                "_id_date", listOf(
                    Workout("_id_workout", 1, "Title", 10)
                )
            )
        )
    )

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MainViewModel()
        viewModel.everfitRepository = everfitRepository
        viewModel.localRepository = localRepository
    }

    @Test
    fun `when call getWorkouts, data is loaded correct`() {
        Mockito.`when`(everfitRepository.getWorkouts()).thenReturn(flow {
            emit(ApiResponse.success(dataTest))
        })
        viewModel.getWorkouts()
        val value = viewModel.workouts.getOrAwaitValue()
        assertEquals(dataTest.data, value)
    }

    @Test
    fun `when have data is cached storage, data is loaded correct`() {
        val dataTest = WorkoutsResponse(
            listOf(
                DateWorkout(
                    "_id_date", listOf(
                        Workout("_id_workout", 1, "Title", 10)
                    )
                )
            )
        )
        Mockito.`when`(localRepository.getWorkouts()).thenReturn(flow {
            emit(dataTest)
        })
        viewModel.getCachedData()
        val value = viewModel.workouts.getOrAwaitValue()
        assertEquals(dataTest.data, value)
    }
}
