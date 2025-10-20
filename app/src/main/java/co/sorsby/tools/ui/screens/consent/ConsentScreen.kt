package co.sorsby.tools.ui.screens.consent

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import co.sorsby.tools.data.local.UserSettingsRepository

class ConsentViewModelFactory(private val repository: UserSettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConsentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConsentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun ConsentScreen(
    context: Context = LocalContext.current
) {
    val repository = UserSettingsRepository(context)
    val viewModel: ConsentViewModel = viewModel(factory = ConsentViewModelFactory(repository))

    Scaffold {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Privacy Consent",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "To help us improve the app, we would like to collect anonymous crash reports and analytics. This helps us find and fix bugs faster. You can change this setting at any time in the app's settings.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                TextButton(onClick = { viewModel.onConsentResult(false) }) {
                    Text("Decline")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.onConsentResult(true) }) {
                    Text("Accept")
                }
            }
        }
    }
}
