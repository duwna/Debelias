package com.duwna.debelias.presentation.screens.game_playing.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun SwipeableWordNative(
    modifier: Modifier = Modifier,
    word: String,
    containerHeightPx: Float,
    onSwiped: (WordSwipeDirection) -> Unit
)
enum class WordSwipeDirection {
    UP,
    CENTER,
    DOWN;
}
