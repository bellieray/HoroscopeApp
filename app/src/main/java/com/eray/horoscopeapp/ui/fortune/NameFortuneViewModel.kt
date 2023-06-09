package com.eray.horoscopeapp.ui.fortune

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.fortune.FortuneRepository
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.ui.match_detail.ConsumableError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameFortuneViewModel @Inject constructor(private val fortuneRepository: FortuneRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow(NameFortuneViewState())
    val viewState get() = _viewState.asStateFlow()

    fun getNameFortuneResult(id: String) {
        viewModelScope.launch {
            when (val response = fortuneRepository.getNameFortuneById(id)) {
                is Result.Success -> {
                    _viewState.update {
                        it.copy(nameFortuneResult = response.data?.firstOrNull())
                    }
                    addEventToList(NameFortuneViewEvent.ShowResult)
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                }
            }
        }
    }

    private fun addEventToList(event: NameFortuneViewEvent?) {
        event?.let {
            val eventList =
                viewState.value.nameFortuneViewEvents?.toMutableList() ?: mutableListOf()
            eventList.add(event)
            _viewState.value =
                viewState.value.copy(nameFortuneViewEvents = eventList, isLoading = false)
        }
    }

    fun eventConsumed(event: NameFortuneViewEvent) {
        _viewState.update { currentUiState ->
            val newEventList = currentUiState.nameFortuneViewEvents?.filterNot { it == event }
            currentUiState.copy(nameFortuneViewEvents = newEventList)
        }
    }

    private fun addErrorToList(exception: Exception?) {
        exception?.let {
            val errorList = viewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _viewState.value = viewState.value.copy(consumableErrors = errorList, isLoading = false)
        }
    }

    fun errorConsumed(errorId: Long) {
        _viewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class NameFortuneViewState(
    val nameFortuneResult: NameFortuneItem? = null,
    val isLoading: Boolean = true,
    val consumableErrors: List<ConsumableError>? = null,
    val nameFortuneViewEvents: List<NameFortuneViewEvent>? = null
)

sealed class NameFortuneViewEvent {
    object ShowResult : NameFortuneViewEvent()
}