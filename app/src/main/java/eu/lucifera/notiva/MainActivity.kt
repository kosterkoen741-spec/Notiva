package eu.lucifera.notiva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.lucifera.notiva.ui.NoteViewModel
import eu.lucifera.notiva.ui.SettingsViewModel
import eu.lucifera.notiva.ui.navigation.Screen
import eu.lucifera.notiva.ui.screens.HelpScreen
import eu.lucifera.notiva.ui.screens.InfoScreen
import eu.lucifera.notiva.ui.screens.NotesScreen
import eu.lucifera.notiva.ui.screens.SettingsScreen
import eu.lucifera.notiva.ui.theme.NotivaTheme

class MainActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
            val languageCode by settingsViewModel.languageCode.collectAsState()
            val navController = rememberNavController()
            var showMenu by remember { mutableStateOf(false) }

            // Apply language when it changes
            LaunchedEffect(languageCode) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }

            NotivaTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                IconButton(onClick = { showMenu = !showMenu }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.menu_notes)) },
                                        onClick = {
                                            navController.navigate(Screen.Notes.route)
                                            showMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.menu_help)) },
                                        onClick = {
                                            navController.navigate(Screen.Help.route)
                                            showMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.menu_settings)) },
                                        onClick = {
                                            navController.navigate(Screen.Settings.route)
                                            showMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.menu_info)) },
                                        onClick = {
                                            navController.navigate(Screen.Info.route)
                                            showMenu = false
                                        }
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Notes.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Notes.route) { NotesScreen(noteViewModel) }
                        composable(Screen.Settings.route) { SettingsScreen(settingsViewModel) }
                        composable(Screen.Help.route) { HelpScreen() }
                        composable(Screen.Info.route) { InfoScreen() }
                    }
                }
            }
        }
    }
}
