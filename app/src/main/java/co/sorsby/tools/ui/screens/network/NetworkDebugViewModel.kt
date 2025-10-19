package co.sorsby.tools.ui.screens.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.sorsby.tools.data.network.NetworkRepository
import co.sorsby.tools.data.network.model.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NetworkDebugViewModel(
    private val repository: NetworkRepository = NetworkRepository()
) : ViewModel() {

    private val _response = MutableStateFlow<NetworkResponse?>(null)
    val response = _response.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun makeRequest(url: String) {
        viewModelScope.launch {
            try {
                val result = repository.executeRequest(url)
                _response.value = result
                _error.value = null
            } catch (e: Exception) {
                Log.i(javaClass.name.toString(), "Error making request", e)
                _error.value = e.localizedMessage ?: "Unknown error"
            }
        }
    }
}
