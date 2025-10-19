package co.sorsby.tools.ui.screens.debug

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import co.sorsby.tools.ui.components.navigation.MyDrawerContent
import org.junit.Rule
import org.junit.Test

class DebugScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun debugScreenContainsMainSections() {
        composeTestRule.setContent {
            DebugScreen()
        }

        composeTestRule.onNodeWithText("App Diagnostics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Trigger Fake Crash").assertIsDisplayed()
        composeTestRule.onNodeWithText("System Info").assertIsDisplayed()
        composeTestRule.onNodeWithText("Show Build Info").assertIsDisplayed()
    }
}
