package co.sorsby.tools.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    val crashlyticsConsent by viewModel.crashlyticsConsent.collectAsState()
    val usageAnalyticsConsent by viewModel.usageAnalyticsConsent.collectAsState()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Allow Crash Reporting")
                    Text("Help us fix bugs by sending anonymous crash reports.", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = crashlyticsConsent,
                    onCheckedChange = { consent -> viewModel.setCrashlyticsConsent(consent) }
                )
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Allow Usage Analytics")
                    Text("Help us improve the app by sending anonymous usage data.", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = usageAnalyticsConsent,
                    onCheckedChange = { consent -> viewModel.setUsageAnalyticsConsent(consent) }
                )
            }
        }
    }
}
