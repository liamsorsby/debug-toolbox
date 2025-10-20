package co.sorsby.tools.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Http
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.sorsby.tools.ui.models.NavigationItem
import co.sorsby.tools.ui.models.Screen
import co.sorsby.tools.ui.screens.debug.DebugScreen
import co.sorsby.tools.ui.screens.network.NetworkDebugScreen
import co.sorsby.tools.ui.screens.settings.SettingsScreen

val bottomNavItems =
    listOf(
        NavigationItem(
            label = "Home",
            icon = Icons.Default.Home,
            route = Screen.Home.route,
        ),
        NavigationItem(
            label = "Network",
            icon = Icons.Default.Http,
            route = Screen.Http.route,
        ),
    )

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            androidx.compose.material3.Text("Home Screen")
        }
        composable(Screen.Http.route) { NetworkDebugScreen() }
        composable(Screen.Debug.route) { DebugScreen() }
        composable(Screen.Settings.route) { SettingsScreen(onNavigateUp = { navController.navigateUp() }) }
    }
}
