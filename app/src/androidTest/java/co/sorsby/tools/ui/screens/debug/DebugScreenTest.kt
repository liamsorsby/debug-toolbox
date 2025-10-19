package co.sorsby.tools.ui.screens.debug

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import co.sorsby.tools.ui.theme.ToolsTheme
import org.junit.Rule
import org.junit.Test

class DebugScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun debugScreen_initialState_displaysCorrectly() {
        composeTestRule.setContent {
            ToolsTheme {
                DebugScreen()
            }
        }

        composeTestRule.onNodeWithText("Debug Tools").assertIsDisplayed()
        composeTestRule.onNodeWithText("App Diagnostics").assertIsDisplayed()
        composeTestRule.onNodeWithText("System Info").assertIsDisplayed()

        composeTestRule.onNodeWithText("Trigger Fake Crash").assertIsDisplayed()
        composeTestRule.onNodeWithText("Show Build Info").assertIsDisplayed()
    }
}
