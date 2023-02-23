package com.duwna.debelias.presentation

sealed class Screen(val route: String) {

    object Main : Screen("main")

    object GameStatus : Screen("game_status")

    object GamePlaying : Screen("game_playing")
}
