package com.eray.horoscopeapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.ui.match.adapter.OtherHoroscope
import com.eray.horoscopeapp.util.getHoroscopeIdFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val horoscopeRepository: HoroscopeRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow(ProfileViewState())
    val viewState = _viewState.asStateFlow()

    @Inject
    lateinit var prefs: Prefs

    init {
        fetchHoroscopes()
    }

    fun fetchHoroscopes() {
        if (_viewState.value.horoscopeList != null) return
        viewModelScope.launch {
            when (val response = horoscopeRepository.getHoroscopes()) {
                is Result.Success -> {
                    _viewState.update { state ->
                        state.copy(horoscopeList = response.data?.map {
                            OtherHoroscope.from(it)
                        })
                    }
                    _viewState.value.horoscopeId?.let {
                        filterHoroscopeListById(it)
                    }
                }
                else -> {}
            }
        }
    }

    fun clearAllValues() {
        viewModelScope.launch {
            prefs.clearAll()
        }
    }

    fun convertDateToHoroscope() {
        val birthTime = _viewState.value.personalDetail?.birthTime ?: return
        viewModelScope.launch {
            _viewState.update { viewState ->
                viewState.copy(horoscopeId = birthTime.getHoroscopeIdFromDate())
            }
        }
    }

    fun setUserInfoModel(personalDetail: PersonalDetail?) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(personalDetail = personalDetail)
            }
            convertDateToHoroscope()
        }
    }

    fun filterHoroscopeListById(horoscopeId: Int) {
        _viewState.value.horoscopeList?.let {
            it.find { it.horoscope.id == horoscopeId.toLong() }.apply {
                _viewState.update { state ->
                    state.copy(horoscopeFromId = this)
                }
            }
        }
    }

    fun setPagerItemList(pagerItems: List<PagerItem>) {
        _viewState.update {
            it.copy(
                pagerItems = pagerItems
            )
        }
    }
}

data class ProfileViewState(
    val horoscopeId: Int? = null,
    val personalDetail: PersonalDetail? = null,
    val horoscopeList: List<OtherHoroscope>? = null,
    val horoscopeFromId: OtherHoroscope? = null,
    val pagerItems: List<PagerItem>? = null
)