package com.alvarez.sergio.actraining.ui.Main

import app.cash.turbine.test
import com.alvarez.sergio.actraining.data.MainViewModel
import com.alvarez.sergio.actraining.data.MainViewModel.*
import com.alvarez.sergio.actraining.data.database.NeoDAO
import com.alvarez.sergio.actraining.testcommonsitems.fakeNeo
import com.alvarez.sergio.actraining.ui.Rules.CoroutinesTestRule
import com.alvarez.sergio.actraining.usecases.GetTodayNeosUseCase
import com.alvarez.sergio.actraining.usecases.RecoveringLocalDailyNeosUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getLocalDailyNeosUseCase: RecoveringLocalDailyNeosUseCase

    @Mock
    lateinit var requestTodayNeosUseCase: GetTodayNeosUseCase

    @Mock
    lateinit var neoDAO: NeoDAO

    private lateinit var viewModel: MainViewModel

    private var neos = listOf(fakeNeo.copy(id = 1))

    @Before
    fun setUp() {
        whenever(getLocalDailyNeosUseCase()).thenReturn(flowOf(neos))
        viewModel = MainViewModel(getLocalDailyNeosUseCase, requestTodayNeosUseCase)
    }

    @Test
    fun `The state is automatically updated with the current chached content`(): Unit = runTest {

        /**
        When the mutableStateFlow is initialized, an empty MainUIState is assigned to it.
        We will create a list that stops with the values obtained, since it is a flow that never stops updating.
        viewModel.mainState.collect{
        assertEquals(MainViewModel.MainUIStates(neos = neos), it)
        }
         */

//        val results = mutableListOf<MainUIStates>()
//        val job = launch { viewModel.mainState.toList(results) }
//        runCurrent()
//        job.cancel()
//
//        assertEquals(MainUIStates(neos = neos), results[0])

        viewModel.mainState.test {
            assertEquals(MainUIStates(), awaitItem())
            assertEquals(MainUIStates(neos = neos), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is showned only when a server request is runnnig`() = runTest {

        viewModel.onUIReady()

        viewModel.mainState.test {
            assertEquals(MainUIStates(), awaitItem())
            assertEquals(MainUIStates(neos = neos), awaitItem())
            assertEquals(MainUIStates(neos = neos, loading = true), awaitItem())
            assertEquals(MainUIStates(neos = neos, loading = false), awaitItem())
            cancel()
        }
    }
}
