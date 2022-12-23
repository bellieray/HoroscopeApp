package com.eray.horoscopeapp.ui.horoscope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoroscopeViewModel @Inject constructor(private val horoscopeRepository: HoroscopeRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(HoroscopeViewState())
    val viewState get() = _viewState.asStateFlow()

    init {
        fetchHoroscopes()
    }

    private fun fetchHoroscopes() {
        viewModelScope.launch {
            when (val response = horoscopeRepository.getHoroscopes()) {
                is Result.Success -> {
                    _viewState.update {
                        it.copy(horoscopeList = response.data)
                    }
                }
                else -> {}
            }
        }
    }
}

data class HoroscopeViewState(
    val horoscopeList: List<Horoscope>? = null,
    val isLoading: Boolean = true
)