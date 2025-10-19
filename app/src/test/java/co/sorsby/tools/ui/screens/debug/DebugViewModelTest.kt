package co.sorsby.tools.ui.screens.debug

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DebugViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DebugViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DebugViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state message is null`() =
        runTest {
            val message = viewModel.message.first()
            assertEquals(null, message)
        }

    @Test
    fun `showBuildInfo updates message with build info`() =
        runTest {
            viewModel.showBuildInfo()
            val message = viewModel.message.first()
            assertTrue(message?.contains("Version:") == true)
            assertTrue(message?.contains("Code:") == true)
            assertTrue(message?.contains("Build Type:") == true)
            assertTrue(message?.contains("Debug:") == true)
        }

    @Test
    fun `triggerCrash throws RuntimeException`() {
        val exception =
            assertThrows(RuntimeException::class.java) {
                viewModel.triggerCrash()
            }
        assertEquals("Fake crash triggered from DebugViewModel", exception.message)
    }

    @Test
    fun `clearCache updates message`() =
        runTest {
            viewModel.clearCache()
            val message = viewModel.message.first()
            assertEquals("Cache cleared", message)
        }

    @Test
    fun `simulateNetworkError updates message`() =
        runTest {
            viewModel.simulateNetworkError()
            val message = viewModel.message.first()
            assertEquals("Simulated network error", message)
        }
}
