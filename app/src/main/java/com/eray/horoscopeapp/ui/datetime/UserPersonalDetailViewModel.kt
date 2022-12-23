package com.eray.horoscopeapp.ui.datetime

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class UserPersonalDetailViewModel : ViewModel() {
    private var _viewState = MutableStateFlow(UserPersonalDetailsViewState())
    val viewState: StateFlow<UserPersonalDetailsViewState> = _viewState

    fun onBirthDateSelected(date: Date) {
        _viewState.value = viewState.value.copy(userBirthDate = date)
    }
}

data class UserPersonalDetailsViewState(
    val userBirthDate: Date? = null
)