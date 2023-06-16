package com.eray.horoscopeapp.ui.match_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eray.horoscopeapp.data.repository.HoroscopeRepository
import com.eray.horoscopeapp.model.Result
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    val repository: HoroscopeRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    private val _viewState = MutableStateFlow(MatchDetailViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getMatchingHoroscopeItem()
        getIds()
    }

    private fun getIds() {
        val firstId = state.get<Int>("firstId").toString()
        val secondId = state.get<Int>("secondId").toString()
        _viewState.update {
            it.copy(firstId = firstId, secondId = secondId)
        }
    }

    private fun getMatchingHoroscopeItem() {
        _viewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response =
                repository.getMatchHoroscopeById(_viewState.value.firstId + _viewState.value.secondId)) {
                is Result.Success -> {
                    if (response.data?.isEmpty() == true) {
                        _viewState.update {
                            it.copy(
                                firstId = _viewState.value.secondId,
                                secondId = _viewState.value.firstId,
                                isLoading = false,
                            )
                        }
                        getMatchingHoroscopeItem()
                    } else {
                        _viewState.update {
                            it.copy(
                                horoscopeMatchItem = response.data?.firstOrNull(),
                                isLoading = false
                            )
                        }
                    }
                }

                is Result.Failed -> {
                    _viewState.update {
                        it.copy(isLoading = false)
                    }

                    addErrorToList(response.exception)
                }
            }
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

data class MatchDetailViewState(
    val horoscopeMatchItem: HoroscopeMatchItem? = null,
    val firstId: String? = null,
    val secondId: String? = null,
    val isLoading: Boolean = false,
    val consumableErrors: List<ConsumableError>? = null,
)

data class HoroscopeMatchItem(
    val id: String?,
    val firstHoroscope: String?,
    val secondHoroscope: String?,
    val firstUrl: String?,
    val secondUrl: String?,
    val generalOverall: String?,
    val sexOverall: String?,
    val loveOverall: String?,
    val relationshipOverall: String?,
    val relationText: String?
) {
    companion object {
        fun toHoroscopeMatchItemList(qs: QuerySnapshot?) =
            qs?.documents?.map {
                HoroscopeMatchItem(
                    id = it.get("id") as? String,
                    firstHoroscope = it.get("firstHoroscope") as? String,
                    secondHoroscope = it.get("secondHoroscope") as? String,
                    firstUrl = it.get("firstUrl") as? String,
                    secondUrl = it.get("secondUrl") as? String,
                    generalOverall = it.get("generalOverall") as? String,
                    sexOverall = it.get("sexOverall") as? String,
                    loveOverall = it.get("loveOverall") as? String,
                    relationshipOverall = it.get("loveOverall") as? String,
                    relationText = it.get("relationText") as? String
                )
            }
    }
}

data class ConsumableError(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val exception: Exception
)