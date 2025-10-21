package co.sorsby.tools.ui.screens.dns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.sorsby.tools.data.local.UserSettingsRepository
import co.sorsby.tools.data.network.DnsResolver

class DnsLookupViewModelFactory(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DnsLookupViewModel::class.java)) {
            return DnsLookupViewModel(DnsResolver(userSettingsRepository)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
