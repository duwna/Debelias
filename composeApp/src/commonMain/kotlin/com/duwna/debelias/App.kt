package com.duwna.debelias

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.Screen
import com.duwna.debelias.presentation.SnackbarScaffold
import com.duwna.debelias.presentation.screens.game_playing.GamePlayingScreen
import com.duwna.debelias.presentation.screens.main.MainScreen
import com.duwna.debelias.presentation.screens.round_result.RoundResultScreen
import com.duwna.debelias.presentation.screens.settings.SettingsScreen
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.LocalKoinScope

@Composable
@Preview
fun App() {
    val koinScope = LocalKoinScope.current

    MaterialTheme {
        Navigation(navigator = koinScope.get<Navigator>()) { navController ->
            val showBackIcon = navController.currentBackStackEntryFlow
                .map { it.destination.route != Screen.Main.route }
                .collectAsState(initial = false)

            SnackbarScaffold(
                messageHandler = koinScope.get<MessageHandler>(),
                showBackIcon = showBackIcon.value,
                onBackClick = { navController.popBackStack() }
            ) { padding ->
                MainNavHost(
                    navController = navController,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun Navigation(
    navigator: Navigator,
    content: @Composable (NavHostController) -> Unit
) {
    val navController = rememberNavController()

    DisposableEffect(navController) {
        navigator.setController(navController)

        onDispose {
            navigator.clear()
        }
    }

    content(navController)
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) { MainScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
        composable(Screen.GamePlaying.route) { GamePlayingScreen() }
        composable(Screen.RoundResult.route) { RoundResultScreen() }
    }
}