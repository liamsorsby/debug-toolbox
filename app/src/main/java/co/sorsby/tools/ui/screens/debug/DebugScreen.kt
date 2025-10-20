package co.sorsby.tools.ui.screens.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.sorsby.tools.data.local.UserSettingsRepository
import co.sorsby.tools.ui.models.DebugAction
import co.sorsby.tools.ui.theme.ToolsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    val context = LocalContext.current
    val userSettingsRepository = remember { UserSettingsRepository(context) }
    val viewModel: DebugViewModel = viewModel(factory = DebugViewModelFactory(userSettingsRepository))

    val message: String? by viewModel.message.collectAsState(initial = null)
    val hasCrashlyticsConsent by viewModel.hasCrashlyticsConsent.collectAsState()
    val hasUsageAnalyticsConsent by viewModel.hasUsageAnalyticsConsent.collectAsState()
    val hasSeenConsentScreen by viewModel.hasSeenConsentScreen.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(message) {
        message?.let {
            scope.launch { snackbarHostState.showSnackbar(it) }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Debug Tools") },
                navigationIcon = {
                    Icon(Icons.Default.BugReport, contentDescription = null)
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                DebugSection(
                    title = "App Diagnostics",
                    items =
                        listOf(
                            DebugAction(
                                "Trigger Fake Crash",
                                Icons.Default.Warning,
                            ) { viewModel.triggerCrash() },
                        ),
                )
            }

            item {
                DebugSection(
                    title = "System Info",
                    items =
                        listOf(
                            DebugAction(
                                "Show Build Info",
                                Icons.Default.BugReport,
                            ) { viewModel.showBuildInfo() },
                        ),
                )
            }

            item {
                Text(
                    text = "User Preferences",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                )
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp)),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Crashlytics Consent")
                        Switch(
                            checked = hasCrashlyticsConsent,
                            onCheckedChange = { viewModel.toggleCrashlyticsConsent() },
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Usage Analytics Consent")
                        Switch(
                            checked = hasUsageAnalyticsConsent,
                            onCheckedChange = { viewModel.toggleUsageAnalyticsConsent() },
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Has Seen Consent Screen")
                        Switch(
                            checked = hasSeenConsentScreen,
                            onCheckedChange = { viewModel.toggleHasSeenConsentScreen() },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Debug Menu - Light")
@Composable
fun DebugScreenPreviewLight() {
    ToolsTheme(darkTheme = false) {
        DebugScreen()
    }
}

@Preview(showBackground = true, name = "Debug Menu - Dark")
@Composable
fun DebugScreenPreviewDark() {
    ToolsTheme(darkTheme = true) {
        DebugScreen()
    }
}
