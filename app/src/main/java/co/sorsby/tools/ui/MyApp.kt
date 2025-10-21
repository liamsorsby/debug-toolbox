package co.sorsby.tools.ui

import android.content.res.Configuration
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.sorsby.tools.BuildConfig
import co.sorsby.tools.ui.models.Screen
import co.sorsby.tools.ui.navigation.AppNavHost
import co.sorsby.tools.ui.navigation.MyDrawerContent
import co.sorsby.tools.ui.navigation.bottomNavItems
import co.sorsby.tools.ui.theme.ToolsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ToolsTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                MyDrawerContent(
                    onNavigate = { route ->
                        scope.launch {
                            drawerState.close()
                            navController.navigate(route)
                        }
                    },
                )
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Toolbox") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Open navigation drawer",
                                )
                            }
                        },
                    )
                },
                bottomBar = {
                    NavigationBar {
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStackEntry?.destination?.route

                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    val iconModifier =
                                        if (BuildConfig.DEBUG && item.route == Screen.Home.route) {
                                            Modifier.combinedClickable(
                                                onClick = { navController.navigate(item.route) },
                                                onLongClick = {
                                                    navController.navigate(Screen.Debug.route)
                                                },
                                            )
                                        } else {
                                            Modifier
                                        }
                                    Icon(
                                        item.icon,
                                        contentDescription = item.label,
                                        modifier = iconModifier,
                                    )
                                },
                                label = { Text(item.label) },
                            )
                        }
                    }
                },
                content = { innerPadding ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        AppNavHost(navController = navController)
                    }
                },
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "MyApp Lightmode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun MyAppPreviewLightMode() {
    ToolsTheme {
        MyApp()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "MyApp Darkmode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun MyAppPreviewDarkMode() {
    ToolsTheme {
        MyApp()
    }
}
