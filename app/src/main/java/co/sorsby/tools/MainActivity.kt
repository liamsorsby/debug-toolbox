package co.sorsby.tools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.sorsby.tools.ui.MyApp
import co.sorsby.tools.ui.screens.consent.ConsentScreen
import co.sorsby.tools.ui.theme.ToolsTheme

class MainViewModelFactory(
    private val application: ToolsApplication,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application.userSettingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(application as ToolsApplication)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToolsTheme {
                val uiState by viewModel.uiState.collectAsState()
                when (uiState) {
                    is MainUiState.Loading -> {
                        // You can show a splash screen or a loading indicator here
                    }
                    is MainUiState.ShowConsent -> {
                        ConsentScreen(onConsentGiven = { viewModel.onConsentGiven() })
                    }
                    is MainUiState.ShowApp -> {
                        MyApp()
                    }
                }
            }
        }
    }
}
