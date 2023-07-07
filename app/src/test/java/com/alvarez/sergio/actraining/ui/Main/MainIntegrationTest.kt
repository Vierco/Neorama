package com.alvarez.sergio.actraining.ui.Main

import app.cash.turbine.test
import com.alvarez.sergio.actraining.appTestShared.buildDatabaseNeos
import com.alvarez.sergio.actraining.appTestShared.buildRepositoryWith
import com.alvarez.sergio.actraining.data.MainViewModel
import com.alvarez.sergio.actraining.data.MainViewModel.MainUIStates
import com.alvarez.sergio.actraining.data.database.NeoEntity
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.testcommonsitems.fakeNeo
import com.alvarez.sergio.actraining.ui.Rules.CoroutinesTestRule
import com.alvarez.sergio.actraining.usecases.GetTodayNeosUseCase
import com.alvarez.sergio.actraining.usecases.RecoveringLocalDailyNeosUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Data loaded from server when theres not data in BD`(): Unit = runTest {
        val remoteData = listOf(fakeNeo.copy(1), fakeNeo.copy(2))
        val viewModel = buildViewModelWith(remoteData = remoteData)

        viewModel.onUIReady()

        viewModel.mainState.test {
            assertEquals(MainUIStates(), awaitItem())
            assertEquals(MainUIStates(neos = emptyList()), awaitItem())
            assertEquals(MainUIStates(neos = emptyList(), loading = true), awaitItem())
            assertEquals(MainUIStates(neos = emptyList(), loading = false), awaitItem())
            assertEquals(MainUIStates(neos = remoteData, loading = false), awaitItem())
            cancel()
        }
    }

    @Test
    fun `data loaded from local DB when available`(): Unit = runTest {
        val remoteData = listOf(fakeNeo.copy(1), fakeNeo.copy(2))
        val localData = buildDatabaseNeos(1, 2, 3)

        val viewModel = buildViewModelWith(localData, remoteData)

        viewModel.mainState.test {
            assertEquals(MainUIStates(), awaitItem())
            val neos = awaitItem().neos!!
            assertEquals("Name 1", neos[0].name)
            assertEquals("Name 2", neos[1].name)
            assertEquals("Name 3", neos[2].name)
        }
    }

    fun buildViewModelWith(
        localData: List<NeoEntity> = emptyList(),
        remoteData: List<NeoEntityDomain> = emptyList()
    ): MainViewModel {

        val neoRepository = buildRepositoryWith(localData, remoteData)

        val getTodayNeosUseCase = GetTodayNeosUseCase(neoRepository)
        val recoveringLocalDailyNeosUseCase = RecoveringLocalDailyNeosUseCase(neoRepository)
        return MainViewModel(recoveringLocalDailyNeosUseCase, getTodayNeosUseCase)
    }
}
