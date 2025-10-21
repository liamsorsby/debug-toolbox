package co.sorsby.tools.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    val crashlyticsConsent by viewModel.crashlyticsConsent.collectAsState()
    val usageAnalyticsConsent by viewModel.usageAnalyticsConsent.collectAsState()
    val dnsServer by viewModel.dnsServer.collectAsState()
    val dnsServer2 by viewModel.dnsServer2.collectAsState()
    val dnsNdots by viewModel.dnsNdots.collectAsState()
    val dnsTimeout by viewModel.dnsTimeout.collectAsState()

    Scaffold {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            Text("Privacy", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Allow Crash Reporting")
                    Text(
                        "Help us fix bugs by sending anonymous crash reports.",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Switch(
                    checked = crashlyticsConsent,
                    onCheckedChange = { consent -> viewModel.setCrashlyticsConsent(consent) },
                )
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Allow Usage Analytics")
                    Text(
                        "Help us improve the app by sending anonymous usage data.",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Switch(
                    checked = usageAnalyticsConsent,
                    onCheckedChange = { consent -> viewModel.setUsageAnalyticsConsent(consent) },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("DNS Tools", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dnsServer,
                onValueChange = { server -> viewModel.setDnsServer(server) },
                label = { Text("DNS Server 1") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dnsServer2,
                onValueChange = { server -> viewModel.setDnsServer2(server) },
                label = { Text("DNS Server 2") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dnsNdots.toString(),
                onValueChange = { ndots -> viewModel.setDnsNdots(ndots) },
                label = { Text("Ndots") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dnsTimeout.toString(),
                onValueChange = { timeout -> viewModel.setDnsTimeout(timeout) },
                label = { Text("Timeout (seconds)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
    }
}
