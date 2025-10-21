package co.sorsby.tools.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: UserSettingsRepository,
) : ViewModel() {
    val crashlyticsConsent: StateFlow<Boolean> =
        repository.crashlyticsConsent
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    val usageAnalyticsConsent: StateFlow<Boolean> =
        repository.usageAnalyticsConsent
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false,
            )

    val dnsServer: StateFlow<String> =
        repository.dnsServer
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    val dnsServer2: StateFlow<String> =
        repository.dnsServer2
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "",
            )

    val dnsNdots: StateFlow<Int> =
        repository.dnsNdots
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 1,
            )

    val dnsTimeout: StateFlow<Int> =
        repository.dnsTimeout
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 5,
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

    fun setDnsServer(server: String) {
        viewModelScope.launch {
            repository.setDnsServer(server)
        }
    }

    fun setDnsServer2(server: String) {
        viewModelScope.launch {
            repository.setDnsServer2(server)
        }
    }

    fun setDnsSearchPaths(paths: String) {
        viewModelScope.launch {
            repository.setDnsSearchPaths(paths.split(",").toSet())
        }
    }

    fun setDnsNdots(ndots: String) {
        viewModelScope.launch {
            repository.setDnsNdots(ndots.toIntOrNull() ?: 1)
        }
    }

    fun setDnsTimeout(timeout: String) {
        viewModelScope.launch {
            repository.setDnsTimeout(timeout.toIntOrNull() ?: 5)
        }
    }
}
