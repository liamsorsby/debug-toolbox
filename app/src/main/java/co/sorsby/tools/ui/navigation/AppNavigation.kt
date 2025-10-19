package co.sorsby.tools.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.sorsby.tools.ui.models.Screen
import co.sorsby.tools.ui.screens.debug.DebugScreen
import co.sorsby.tools.ui.screens.network.NetworkDebugScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            androidx.compose.material3.Text("Home Screen")
        }
        composable(Screen.Http.route) { NetworkDebugScreen() }
        composable(Screen.Debug.route) { DebugScreen() }
    }
}
