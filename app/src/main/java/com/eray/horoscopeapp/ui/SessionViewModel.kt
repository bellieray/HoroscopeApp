package com.eray.horoscopeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.util.Constants.IS_LANGUAGE_ENGLISH
import com.eray.horoscopeapp.util.Constants.IS_LANGUAGE_SELECTED
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


    fun setUserDetails() {
        viewModelScope.launch {
            prefs.getSharedString(USER_INFOS).collect { infos ->
                _viewState.update {
                    it.copy(personalDetail = Gson().fromJson(infos, PersonalDetail::class.java))
                }
            }
        }

    }

    fun getLanguage() {
        viewModelScope.launch {
            prefs.getSharedBoolean(IS_LANGUAGE_ENGLISH).collect { isEnglish ->
                _viewState.update {
                    it.copy(isEnglish = isEnglish)
                }
            }
        }
    }

    fun setLanguage(isEnglish: Boolean) {
        viewModelScope.launch {
            prefs.setSharedBoolean(IS_LANGUAGE_ENGLISH, isEnglish)
        }
    }

    fun setIsLanguageSelected() {
        viewModelScope.launch {
            prefs.setSharedBoolean(IS_LANGUAGE_SELECTED, true)
            _viewState.update {
                it.copy(isLanguageSelected = true)
            }
        }
    }

    fun getIsLanguageSelected() {
        viewModelScope.launch {
            prefs.getSharedBoolean(IS_LANGUAGE_SELECTED).collect { isLanguageSelected ->
                _viewState.update { it.copy(isLanguageSelected = isLanguageSelected) }
            }
        }
    }
}

data class SessionViewState(
    val isLoggedIn: Boolean? = null,
    val personalDetail: PersonalDetail? = null,
    val isEnglish: Boolean? = null,
    val isLanguageSelected: Boolean? = null
)