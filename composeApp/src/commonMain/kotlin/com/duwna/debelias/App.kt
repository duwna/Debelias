package com.duwna.debelias

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.presentation.Screen
import com.duwna.debelias.presentation.screens.game_playing.GamePlayingScreen
import com.duwna.debelias.presentation.screens.main.MainScreen
import com.duwna.debelias.presentation.screens.round_result.RoundResultScreen
import com.duwna.debelias.presentation.screens.settings.SettingsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.LocalKoinScope

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigation {
            MainNavHost(it)
        }
    }
}

@Composable
fun Navigation(content: @Composable (NavHostController) -> Unit) {
    val navController = rememberNavController()
    val koinScope = LocalKoinScope.current

    DisposableEffect(navController) {
        val navigator = koinScope.get<Navigator>()

        navigator.setController(navController)

        onDispose {
            navigator.clear()
        }
    }

    content(navController)
}

@Composable
private fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { MainScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
        composable(Screen.GamePlaying.route) { GamePlayingScreen() }
        composable(Screen.RoundResult.route) { RoundResultScreen() }
    }
}