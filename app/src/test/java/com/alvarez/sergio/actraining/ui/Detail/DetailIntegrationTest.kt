package com.alvarez.sergio.actraining.ui.Detail

import app.cash.turbine.test
import com.alvarez.sergio.actraining.appTestShared.buildDatabaseNeos
import com.alvarez.sergio.actraining.appTestShared.buildRepositoryWith
import com.alvarez.sergio.actraining.data.database.NeoEntity
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.ui.Detail.DetailViewModel.UiState
import com.alvarez.sergio.actraining.ui.Rules.CoroutinesTestRule
import com.alvarez.sergio.actraining.usecases.FindNeoByIdUseCase
import com.alvarez.sergio.actraining.usecases.SwitchSaveNeoUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTest {
//
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Save Neo action is updated in local data source`(): Unit = runTest {
        val viewModel = buildViewModelWith(
            id = 2,
            localData = buildDatabaseNeos(1, 2, 3)
        )

        viewModel.onNeoToObserveClicked()

        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(false, awaitItem().neo!!.is_potentially_hazardous_asteroid)
            assertEquals(true, awaitItem().neo!!.is_potentially_hazardous_asteroid)
        }
    }

    @Test
    fun `Neo is showed on DetailFragment start`() = runTest {
        val viewModel = buildViewModelWith(
            id = 2,
            localData = buildDatabaseNeos(1, 2, 3)
        )

        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(2, awaitItem().neo!!.id)
            cancel()
        }
    }

    fun buildViewModelWith(
        id: Int,
        localData: List<NeoEntity> = emptyList(),
        remoteData: List<NeoEntityDomain> = emptyList()
    ): DetailViewModel {
        val neoRepository = buildRepositoryWith(localData, remoteData)

        val findNeoByIdUseCase = FindNeoByIdUseCase(neoRepository)
        val switchSaveNeoUseCase = SwitchSaveNeoUseCase(neoRepository)
        return DetailViewModel(id, findNeoByIdUseCase, switchSaveNeoUseCase)
    }
}
