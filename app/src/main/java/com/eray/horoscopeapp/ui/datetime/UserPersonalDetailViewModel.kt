package com.eray.horoscopeapp.ui.datetime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.pref.Prefs
import com.eray.horoscopeapp.model.PersonalDetail
import com.eray.horoscopeapp.util.Constants
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserPersonalDetailViewModel @Inject constructor(private val prefs: Prefs) : ViewModel() {
    private var _viewState = MutableStateFlow(UserPersonalDetailsViewState())
    val viewState: StateFlow<UserPersonalDetailsViewState> = _viewState

    fun onBirthDateSelected(date: Date) {
        _viewState.value = viewState.value.copy(userBirthDate = date)
    }

    fun setUserInfo(name: String, gender: String, birthday: String) {
        viewModelScope.launch {
            prefs.setSharedString(
                Constants.USER_INFOS,
                Gson().toJson(PersonalDetail(name, gender, birthday))
            )
            prefs.setSharedBoolean(Constants.LOGIN_STATE_PREF, true)
        }
    }
}

data class UserPersonalDetailsViewState(
    val userBirthDate: Date? = null
)