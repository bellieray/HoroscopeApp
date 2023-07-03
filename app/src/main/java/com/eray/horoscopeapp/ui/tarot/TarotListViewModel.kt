package com.eray.horoscopeapp.ui.tarot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Tarot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarotListViewModel @Inject constructor(private val horoscopeRepository: HoroscopeRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(TarotListViewState())
    val viewState get() = _viewState.asStateFlow()

    fun fetchTarots(isEnglish: Boolean) {
        _viewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = horoscopeRepository.getTarots(isEnglish)) {
                is com.eray.horoscopeapp.model.Result.Success -> {
                    _viewState.update {
                        it.copy(tarots = response.data, isLoading = false)
                    }
                }
                else -> {
                    _viewState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

data class TarotListViewState(
    val tarots: List<Tarot>? = null,
    val isLoading: Boolean? = null
)