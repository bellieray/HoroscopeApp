package com.eray.horoscopeapp.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.util.checkHoroscope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchViewModel @Inject constructor(private val horoscopeRepository: HoroscopeRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(MatchViewState())
    val viewState = _viewState.asStateFlow()

    fun fetchHoroscopes() {
        viewModelScope.launch {
            when (val response = horoscopeRepository.getHoroscopes()) {
                is Result.Success -> {
                    _viewState.update { state ->
                        state.copy(horoscopeList = response.data?.map {
                            OtherHoroscope.from(it)
                        })
                    }
                }
                else -> {}
            }
        }
    }

    fun convertDateToHoroscope() {
        viewModelScope.launch {
            _viewState.update { viewState ->
                viewState.copy(horoscope = "06/02".checkHoroscope())
            }
        }
    }

    fun setUserInfoModel(personalDetail: PersonalDetail) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(personalDetail = personalDetail)
            }
        }
    }

    fun setBottomSheetModel(otherHoroscope: OtherHoroscope) {
        _viewState.update {
            it.copy(otherHoroscope = otherHoroscope)
        }
    }

    fun listConsumed() {
        _viewState.update {
            it.copy(horoscopeList = null)
        }
    }

}

data class MatchViewState(
    val horoscope: Pair<String, Int>? = null,
    val personalDetail: PersonalDetail? = null,
    val horoscopeList: List<OtherHoroscope>? = null,
    val otherHoroscope: OtherHoroscope? = null
)