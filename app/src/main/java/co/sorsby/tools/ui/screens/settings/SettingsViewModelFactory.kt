package co.sorsby.tools.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.sorsby.tools.data.UserSettingsRepository

class SettingsViewModelFactory(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(userSettingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
