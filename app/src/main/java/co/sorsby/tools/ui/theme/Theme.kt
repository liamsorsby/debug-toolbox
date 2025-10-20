package co.sorsby.tools.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicMaterialThemeState

@Composable
fun ToolsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val dynamicThemeState =
        rememberDynamicMaterialThemeState(
            isDark = darkTheme,
            style = PaletteStyle.TonalSpot,
            contrastLevel = 0.5,
            specVersion = ColorSpec.SpecVersion.SPEC_2025,
            primary = Primary,
        )

    DynamicMaterialTheme(
        state = dynamicThemeState,
        animate = true,
        content = content,
    )
}
