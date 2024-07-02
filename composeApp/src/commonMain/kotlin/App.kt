import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import di.AppModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.Screen
import presentation.screens.game_playing.GamePlayingScreen
import presentation.screens.main.MainScreen
import presentation.screens.round_result.RoundResultScreen
import presentation.screens.settings.SettingsScreen

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

    DisposableEffect(key1 = navController) {
        AppModule.navigator.setController(navController)
        onDispose {
            AppModule.navigator.clear()
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