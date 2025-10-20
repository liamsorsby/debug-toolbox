package co.sorsby.tools.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: UserSettingsRepository) : ViewModel() {

    val crashlyticsConsent: StateFlow<Boolean> = repository.crashlyticsConsent
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val usageAnalyticsConsent: StateFlow<Boolean> = repository.usageAnalyticsConsent
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun setCrashlyticsConsent(hasConsented: Boolean) {
        viewModelScope.launch {
            repository.setCrashlyticsConsent(hasConsented)
        }
    }

    fun setUsageAnalyticsConsent(hasConsented: Boolean) {
        viewModelScope.launch {
            repository.setUsageAnalyticsConsent(hasConsented)
        }
    }
}
