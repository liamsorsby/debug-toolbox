package co.sorsby.tools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface MainUiState {
    object Loading : MainUiState
    object ShowConsent : MainUiState
    object ShowApp : MainUiState
}

class MainViewModel(repository: UserSettingsRepository) : ViewModel() {

    val uiState: StateFlow<MainUiState> = repository.hasSeenConsentScreen
        .map { hasSeen ->
            if (hasSeen) {
                MainUiState.ShowApp
            } else {
                MainUiState.ShowConsent
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading
        )
}
