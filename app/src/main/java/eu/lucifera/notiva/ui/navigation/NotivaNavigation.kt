package eu.lucifera.notiva.ui.navigation

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object Settings : Screen("settings")
    object Help : Screen("help")
    object Info : Screen("info")
}
