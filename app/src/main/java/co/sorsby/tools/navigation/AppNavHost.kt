package co.sorsby.tools.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import co.sorsby.tools.ui.screens.debug.DebugScreen
import co.sorsby.tools.ui.screens.network.NetworkDebugScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = "home") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            androidx.compose.material3.Text("Home Screen")
        }
        composable("http") { NetworkDebugScreen() }
        composable("debug") { DebugScreen() }
    }
}