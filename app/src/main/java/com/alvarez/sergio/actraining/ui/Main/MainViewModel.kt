package com.alvarez.sergio.actraining.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.usecases.GetTodayNeosUseCase
import com.alvarez.sergio.actraining.usecases.RecoveringLocalDailyNeosUseCase
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
class MainViewModel @Inject constructor(
    private val getLocalDailyNeos: RecoveringLocalDailyNeosUseCase,
    private val requestTodayNeosUseCase: GetTodayNeosUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getLocalDailyNeos()
                .catch { cause -> _mainState.update { it.copy(error = cause.toError()) } }
                .collect { neos ->
                    _mainState.update { MainUIStates(neos = neos) }
                }
        }
    }

    data class MainUIStates(
        val loading: Boolean = false,
        var neos: List<NeoEntityDomain>? = null,
        val error: CustomError? = null
    )

    private val _mainState = MutableStateFlow(MainUIStates())
    val mainState: StateFlow<MainUIStates> = _mainState.asStateFlow()

    fun onUIReady() = with(_mainState.value) {
        if (neos == null || neos!!.isEmpty()) {
            viewModelScope.launch {
                _mainState.value = _mainState.value.copy(loading = true)
                val possibleError = requestTodayNeosUseCase()
                _mainState.value = _mainState.value.copy(loading = false, error = possibleError)
            }
        }
    }
}
