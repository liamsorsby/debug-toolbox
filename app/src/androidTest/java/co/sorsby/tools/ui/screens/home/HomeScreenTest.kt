package co.sorsby.tools.ui.screens.home

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.sorsby.tools.ui.models.Screen
import co.sorsby.tools.ui.navigation.AppNavHost
import co.sorsby.tools.ui.theme.ToolsTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            val context = LocalContext.current
            navController =
                TestNavHostController(context).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }

            ToolsTheme {
                AppNavHost(navController = navController)
            }
        }
    }

    @Test
    fun displaysAllHomeDestinations() {
        composeTestRule.onNodeWithText("DNS Lookup").assertExists()
        composeTestRule.onNodeWithText("Http Debug").assertExists()
        composeTestRule.onNodeWithText("Settings").assertExists()
    }

    @Test
    fun clickingDNSTileNavigates() {
        composeTestRule.onNodeWithText("DNS Lookup").performClick()

        composeTestRule.waitForIdle()
        assertEquals(Screen.Dns.route, navController.currentDestination?.route)
    }

    @Test
    fun clickingHTTPTileNavigates() {
        composeTestRule.onNodeWithText("Http Debug").performClick()

        composeTestRule.waitForIdle()
        assertEquals(Screen.Http.route, navController.currentDestination?.route)
    }

    @Test
    fun clickingSettingsTileNavigates() {
        composeTestRule.onNodeWithText("Settings").performClick()

        composeTestRule.waitForIdle()
        assertEquals(Screen.Settings.route, navController.currentDestination?.route)
    }
}
