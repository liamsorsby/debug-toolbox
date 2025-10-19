package co.sorsby.tools.ui.navigation

import android.content.res.Configuration
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
import co.sorsby.tools.ui.models.Screen

@Composable
fun MyDrawerContent(onNavigate: (String) -> Unit) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Tools",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            HorizontalDivider()

            Text(
                "Main Tools",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
            NavigationDrawerItem(
                label = { Text("Home") },
                selected = false,
                onClick = { onNavigate(Screen.Home.route) }
            )
            NavigationDrawerItem(
                label = { Text("Network Debugging") },
                selected = false,
                onClick = { onNavigate(Screen.Http.route) }
            )
        }
    }
}

@Composable
fun MyNavigationDrawer(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MyDrawerContent(
                onNavigate = onNavigate
            )
        }
    ) {
        content()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Open Drawer",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DrawerClosedLightModePreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MyNavigationDrawer(drawerState = drawerState, onNavigate = {}) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Open Drawer",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerClosedDarkModePreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MyNavigationDrawer(drawerState = drawerState, onNavigate = {}) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Closed Drawer",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerOpenDarkModePreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    MyNavigationDrawer(drawerState = drawerState, onNavigate = {}) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Closed Drawer",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DrawerOpenLightModePreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    MyNavigationDrawer(drawerState = drawerState, onNavigate = {}) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Main Content Area")
        }
    }
}
