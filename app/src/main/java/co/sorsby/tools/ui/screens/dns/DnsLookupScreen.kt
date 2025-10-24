package co.sorsby.tools.ui.screens.dns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.sorsby.tools.ToolsApplication
import org.xbill.DNS.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnsLookupScreen() {
    val context = LocalContext.current.applicationContext as ToolsApplication
    val viewModel: DnsLookupViewModel = viewModel(factory = DnsLookupViewModelFactory(context.userSettingsRepository))
    val uiState by viewModel.uiState.collectAsState()

    val recordTypes =
        remember {
            listOf("A", "AAAA", "CNAME", "MX", "NS", "PTR", "SOA", "SRV", "TXT")
        }
    var expanded by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
        ) {
            OutlinedTextField(
                value = uiState.hostname,
                onValueChange = viewModel::onHostnameChange,
                label = { Text("Hostname") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f),
                ) {
                    OutlinedTextField(
                        value = Type.string(uiState.type),
                        onValueChange = { _ -> },
                        readOnly = true,
                        label = { Text("Record Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        recordTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    viewModel.onTypeChange(type)
                                    expanded = false
                                },
                            )
                        }
                    }
                }

                Button(
                    onClick = viewModel::performLookup,
                    enabled = !uiState.isLoading && uiState.hostname.isNotBlank(),
                ) {
                    Text("Lookup")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp),
                    )
                }
                uiState.results.isNotEmpty() -> {
                    Text(
                        text = uiState.results,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                    HorizontalDivider()
                }
                else -> {
                    // Initial state or no results
                }
            }
        }
    }
}
