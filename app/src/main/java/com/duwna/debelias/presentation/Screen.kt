package com.duwna.debelias.presentation

sealed class Screen(val route: String) {

    object Main : Screen("main")

    object Settings : Screen("game_status")

    object GamePlaying : Screen("game_playing")

    object RoundResult : Screen("round_result")
}
