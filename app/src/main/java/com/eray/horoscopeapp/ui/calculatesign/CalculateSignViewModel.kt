package com.eray.horoscopeapp.ui.calculatesign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.util.getHoroscopeIdFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalculateSignViewModel @Inject constructor(private val horoscopeRepository: HoroscopeRepository) :
    ViewModel() {
    private var _viewState = MutableStateFlow(CalculateSignViewState())
    val viewState: StateFlow<CalculateSignViewState> = _viewState

    init {
        fetchHoroscopes()
    }

    private fun fetchHoroscopes() {
        if (_viewState.value.horoscopeList != null) return
        viewModelScope.launch {
            when (val response = horoscopeRepository.getHoroscopes()) {
                is Result.Success -> {
                    _viewState.update { state ->
                        state.copy(horoscopeList = response.data)
                    }
                }
                else -> {}
            }
        }
    }

    fun filterHoroscopeListById(horoscopeId: Int) {
        _viewState.value.horoscopeList?.let {
            it.find { it.id == horoscopeId.toLong() }.apply {
                _viewState.update { state ->
                    state.copy(userHoroscopeId = this?.id?.toInt(), userHoroscope = this)
                }
            }
        }
    }

    fun onBirthDateSelected(date: Date) {
        _viewState.value = viewState.value.copy(userBirthDate = date)
    }

    fun onBirthDateStringSelected(date: String) {
        _viewState.value = viewState.value.copy(userBirthDateString = date)
    }

    fun onBirthTimeSelected(time: Long) {
        _viewState.value = viewState.value.copy(userBirthTime = time)
    }

    fun convertDateToHoroscope() {
        val birthDate = _viewState.value.userBirthDateString ?: return
        viewModelScope.launch {
            _viewState.update { viewState ->
                viewState.copy(userHoroscopeId = birthDate.getHoroscopeIdFromDate())
            }
            _viewState.value.userHoroscopeId?.let { filterHoroscopeListById(it) }
        }
    }
}

data class CalculateSignViewState(
    val userBirthDate: Date? = null,
    val userBirthDateString: String? = null,
    val userBirthTime: Long? = null,
    val userHoroscopeId: Int? = null,
    val userHoroscope: Horoscope? = null,
    val horoscopeList: List<Horoscope>? = null
)