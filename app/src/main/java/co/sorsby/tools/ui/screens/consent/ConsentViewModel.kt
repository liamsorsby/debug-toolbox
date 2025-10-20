package co.sorsby.tools.ui.screens.consent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.local.UserSettingsRepository
import kotlinx.coroutines.launch

class ConsentViewModel(private val repository: UserSettingsRepository) : ViewModel() {

    fun onConsentResult(hasConsented: Boolean) {
        viewModelScope.launch {
            repository.setAnalyticsConsent(hasConsented)
            repository.setHasSeenConsentScreen(true)
        }
    }
}
