package com.eray.horoscopeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Horoscope
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.model.Result
import com.eray.horoscopeapp.util.Constants.LOGIN_STATE_PREF
import com.eray.horoscopeapp.util.Constants.USER_INFOS
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(private val prefs: Prefs) : ViewModel() {
    private val _viewState = MutableStateFlow(SessionViewState())
    val viewState = _viewState.asStateFlow()

    fun setLoginState() {
        viewModelScope.launch {
            prefs.getSharedBoolean(LOGIN_STATE_PREF).collect { loginState ->
                _viewState.update {
                    it.copy(isLoggedIn = loginState)
                }
            }

        }
    }

    fun getHoroscopeFromBirthTime() {

    }

    fun setUserDetails() {
        viewModelScope.launch {
            prefs.getSharedString(USER_INFOS).collect {infos ->
                _viewState.update {
                    it.copy(personalDetail = Gson().fromJson(infos, PersonalDetail::class.java))
                }
            }
        }

    }
}

data class SessionViewState(
    val isLoggedIn: Boolean? = null,
    val personalDetail: PersonalDetail? = null
)