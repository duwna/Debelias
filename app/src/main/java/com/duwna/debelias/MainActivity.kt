package com.duwna.debelias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.duwna.debelias.presentation.theme.DebeliasTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var messageHandler: MessageHandler

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebeliasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Navigation {
                        SnackbarScaffold(messageHandler) {
                            MainNavHost(navController = it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Navigation(content: @Composable (NavHostController) -> Unit) {
        val navController = rememberNavController()

        DisposableEffect(key1 = navController) {
            navigator.setController(navController)
            onDispose {
                navigator.clear()
            }
        }

        content(navController)
    }

    @Composable
    private fun MainNavHost(navController: NavHostController) {
        NavHost(navController, startDestination = Screen.RoundResult.route) {
            composable(Screen.Main.route) { MainScreen() }
            composable(Screen.GameStatus.route) { SettingsScreen() }
            composable(Screen.GamePlaying.route) { GamePlayingScreen() }
            composable(Screen.RoundResult.route) { RoundResultScreen() }
        }
    }
}
