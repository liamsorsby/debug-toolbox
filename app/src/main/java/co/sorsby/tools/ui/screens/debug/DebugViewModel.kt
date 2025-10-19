package co.sorsby.tools.ui.screens.debug

import androidx.lifecycle.ViewModel
import co.sorsby.tools.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DebugViewModel: ViewModel() {
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun showBuildInfo() {
        val info = """
            Version: ${BuildConfig.VERSION_NAME}
            Code: ${BuildConfig.VERSION_CODE}
            Build Type: ${BuildConfig.BUILD_TYPE}
            Debug: ${BuildConfig.DEBUG}
        """.trimIndent()

        _message.value = info
    }

    fun triggerCrash() {
        throw RuntimeException("Fake crash triggered from DebugViewModel")
    }

    fun clearCache() {
        // Example debug action
        _message.value = "Cache cleared"
    }

    fun simulateNetworkError() {
        _message.value = "Simulated network error"
    }
}