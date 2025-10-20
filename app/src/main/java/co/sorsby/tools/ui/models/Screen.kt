package co.sorsby.tools.ui.models

sealed class Screen(
    val route: String,
) {
    data object Home : Screen("home")

    data object Http : Screen("http")

    data object Debug : Screen("debug")

    data object Settings : Screen("settings")
}
