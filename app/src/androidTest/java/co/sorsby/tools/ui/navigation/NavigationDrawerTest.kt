package co.sorsby.tools.ui.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.Test

class MyDrawerContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun drawerContainsMainSections() {
        composeTestRule.setContent {
            MyDrawerContent(onNavigate = {})
        }

        composeTestRule.onNodeWithText("Main Tools").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Network Debugging").assertIsDisplayed()
    }
}
