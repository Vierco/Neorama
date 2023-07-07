package com.alvarez.sergio.actraining.ui.Detail

import app.cash.turbine.test
import com.alvarez.sergio.actraining.testcommonsitems.fakeNeo
import com.alvarez.sergio.actraining.ui.Detail.DetailViewModel.UiState
import com.alvarez.sergio.actraining.ui.Rules.CoroutinesTestRule
import com.alvarez.sergio.actraining.usecases.FindNeoByIdUseCase
import com.alvarez.sergio.actraining.usecases.SwitchSaveNeoUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findNeoUseCase: FindNeoByIdUseCase

    @Mock
    lateinit var switchSaveNeoUseCase: SwitchSaveNeoUseCase

    private lateinit var viewModel: DetailViewModel

    private var neo = fakeNeo.copy(id = 2)

    @Before
    fun setUp() {
        whenever(findNeoUseCase(2)).thenReturn(flowOf(neo))
        viewModel = DetailViewModel(2, findNeoUseCase, switchSaveNeoUseCase)
    }

    @Test
    fun `Detail view is updated when start with the Neos selected by user`() = runTest {
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(neo = neo), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Save action calls it´s use case`() = runTest {
        viewModel.onNeoToObserveClicked()
        runCurrent()

        verify(switchSaveNeoUseCase).invoke(neo)
    }
}
