package com.alvarez.sergio.actraining.ui.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarez.sergio.actraining.di.NeoId
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.usecases.FindNeoByIdUseCase
import com.alvarez.sergio.actraining.usecases.SwitchSaveNeoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @NeoId private val neoId: Int,
    findNeoByIdUseCase: FindNeoByIdUseCase,
    private val switchSaveNeoUseCase: SwitchSaveNeoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findNeoByIdUseCase(neoId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { neo -> _state.value = UiState(neo = neo) }
        }
    }

    fun onNeoToObserveClicked() {
        viewModelScope.launch {
            _state.value.neo?.let { neo ->
                val error = switchSaveNeoUseCase(neo)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(
        val neo: NeoEntityDomain? = null,
        val error: CustomError? = null
    )
}
