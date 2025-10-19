package co.sorsby.tools.ui.components.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.sorsby.tools.BuildConfig

@Composable
fun MyDrawerContent(showDebug: Boolean, onNavigate: (String) -> Unit) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            Text("Tools", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()

            Text("Main Tools", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Home") },
                selected = false,
                onClick = { onNavigate("home") }
            )
            NavigationDrawerItem(
                label = { Text("Network Debugging") },
                selected = false,
                onClick = { onNavigate("http") }
            )

            if (showDebug) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    "Debug Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    badge = { Text("20") }, // Placeholder
                    onClick = { onNavigate("debug") }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun MyNavigationDrawer(
    drawerState: DrawerState,
    showDebug: Boolean = BuildConfig.DEBUG,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MyDrawerContent(
            showDebug,
            onNavigate = onNavigate
        ) }
    ) {
        content()
    }
}

@Preview(showBackground = true, name = "Open Drawer")
@Composable
fun DrawerClosedPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MyNavigationDrawer(drawerState = drawerState, showDebug = false, onNavigate = {} ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}

@Preview(showBackground = true, name = "Closed Drawer")
@Composable
fun DrawerOpenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    MyNavigationDrawer(drawerState = drawerState, showDebug = false, onNavigate = {} ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}

@Preview(showBackground = true, name = "Closed Drawer")
@Composable
fun DrawerOpenDebugPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    MyNavigationDrawer(drawerState = drawerState, showDebug = true, onNavigate = {} ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}
