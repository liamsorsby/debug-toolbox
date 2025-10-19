package co.sorsby.tools.ui.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import co.sorsby.tools.ui.components.navigation.MyDrawerContent
import org.junit.Rule
import org.junit.Test

class MyDrawerContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun drawerContainsMainSections() {
        composeTestRule.setContent {
            MyDrawerContent(showDebug = false, onNavigate = {})
        }

        composeTestRule.onNodeWithText("Main Tools").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Debug Menu").assertDoesNotExist()
        composeTestRule.onNodeWithText("Settings").assertDoesNotExist()
    }

    @Test
    fun drawerContainsMainSectionsWithDebug() {
        composeTestRule.setContent {
            MyDrawerContent(showDebug = true, onNavigate = {})
        }

        composeTestRule.onNodeWithText("Main Tools").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Debug Menu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }
}
