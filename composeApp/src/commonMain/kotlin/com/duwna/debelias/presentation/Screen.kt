package com.duwna.debelias.presentation

sealed class Screen(val route: String) {

    data object Main : Screen("main")

    data object Settings : Screen("game_status")

    data object GamePlaying : Screen("game_playing")

    data object RoundResult : Screen("round_result")
}
