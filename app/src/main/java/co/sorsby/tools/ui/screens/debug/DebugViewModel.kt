package co.sorsby.tools.ui.screens.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DebugViewModel(private val userSettingsRepository: UserSettingsRepository) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    val hasCrashlyticsConsent: StateFlow<Boolean> =
        userSettingsRepository.crashlyticsConsent.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val hasUsageAnalyticsConsent: StateFlow<Boolean> =
        userSettingsRepository.usageAnalyticsConsent.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val hasSeenConsentScreen: StateFlow<Boolean> =
        userSettingsRepository.hasSeenConsentScreen.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    fun triggerCrash() {
        FirebaseCrashlytics.getInstance().log("Manually triggered crash from debug menu")
        throw RuntimeException("Test Crash")
    }

    fun showBuildInfo() {
        viewModelScope.launch {
            _message.emit("Not implemented yet")
        }
    }

    fun toggleCrashlyticsConsent() {
        viewModelScope.launch {
            userSettingsRepository.toggleCrashlyticsConsent()
        }
    }

    fun toggleUsageAnalyticsConsent() {
        viewModelScope.launch {
            userSettingsRepository.toggleUsageAnalyticsConsent()
        }
    }

    fun toggleHasSeenConsentScreen() {
        viewModelScope.launch {
            userSettingsRepository.toggleHasSeenConsentScreen()
        }
    }
}

class DebugViewModelFactory(private val userSettingsRepository: UserSettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebugViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DebugViewModel(userSettingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
