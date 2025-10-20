package co.sorsby.tools.ui.screens.consent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConsentViewModel(private val userSettingsRepository: UserSettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ConsentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    hasCrashlyticsConsent = userSettingsRepository.crashlyticsConsent.first(),
                    hasUsageAnalyticsConsent = userSettingsRepository.usageAnalyticsConsent.first()
                )
            }
        }
    }

    fun toggleCrashlyticsConsent(consent: Boolean) {
        _uiState.update { it.copy(hasCrashlyticsConsent = consent) }
    }

    fun toggleUsageAnalyticsConsent(consent: Boolean) {
        _uiState.update { it.copy(hasUsageAnalyticsConsent = consent) }
    }

    fun onDone() {
        viewModelScope.launch {
            userSettingsRepository.setCrashlyticsConsent(_uiState.value.hasCrashlyticsConsent)
            userSettingsRepository.setUsageAnalyticsConsent(_uiState.value.hasUsageAnalyticsConsent)
            userSettingsRepository.setHasSeenConsentScreen(true)
        }
    }
}

data class ConsentUiState(
    val hasCrashlyticsConsent: Boolean = false,
    val hasUsageAnalyticsConsent: Boolean = false,
)

class ConsentViewModelFactory(private val userSettingsRepository: UserSettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConsentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConsentViewModel(userSettingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
