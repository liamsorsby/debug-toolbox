package co.sorsby.tools.ui.screens.dns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.network.DnsResolver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.xbill.DNS.Type

data class DnsLookupUiState(
    val hostname: String = "google.com",
    val type: Int = Type.A,
    val results: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

class DnsLookupViewModel(
    private val dnsResolver: DnsResolver,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DnsLookupUiState())
    val uiState = _uiState.asStateFlow()

    fun onHostnameChange(hostname: String) {
        _uiState.update { it.copy(hostname = hostname) }
    }

    fun onTypeChange(type: String) {
        _uiState.update { it.copy(type = Type.value(type)) }
    }

    fun performLookup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, results = "") }

            val result = dnsResolver.lookup(_uiState.value.hostname, _uiState.value.type)

            result.fold(
                onSuccess = { records ->
                    val resultsString = records?.joinToString("\n") { it.rdataToString() } ?: "No records found."
                    _uiState.update { it.copy(isLoading = false, results = resultsString) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message ?: "An unknown error occurred.") }
                },
            )
        }
    }
}
