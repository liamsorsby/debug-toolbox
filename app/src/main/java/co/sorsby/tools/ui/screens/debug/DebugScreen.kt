package co.sorsby.tools.ui.screens.debug

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.sorsby.tools.ui.models.DebugAction
import co.sorsby.tools.ui.theme.ToolsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(
    viewModel: DebugViewModel = viewModel()
) {
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show snackbar when message updates
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
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                DebugSection(
                    title = "App Diagnostics",
                    items = listOf(
                        DebugAction(
                            "Trigger Fake Crash",
                            Icons.Default.Warning
                        ) { viewModel.triggerCrash() }
                    )
                )
            }

            item {
                DebugSection(
                    title = "System Info",
                    items = listOf(
                        DebugAction(
                            "Show Build Info",
                            Icons.Default.BugReport
                        ) { viewModel.showBuildInfo() }
                    )
                )
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

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Debug Menu - Dark")
@Composable
fun DebugScreenPreviewDark() {
    ToolsTheme(darkTheme = true) {
        DebugScreen()
    }
}
