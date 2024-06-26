@file:OptIn(ExperimentalMaterialApi::class)

package com.duwna.debelias.presentation.screens.game_playing.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeProgress
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun SwipeableWord(
    modifier: Modifier = Modifier,
    word: String,
    onSwiped: (WordSwipeDirection) -> Unit
) {
    val swipeableState = rememberSwipeableState(WordSwipeDirection.CENTER.index)
    val maxSwipeOffset = LocalConfiguration.current.screenHeightDp.toFloat()

    val anchors = remember {
        mapOf(
            -maxSwipeOffset to WordSwipeDirection.UP.index,
            0f to WordSwipeDirection.CENTER.index,
            maxSwipeOffset to WordSwipeDirection.DOWN.index
        )
    }

    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                onSwiped.invoke(WordSwipeDirection.fromIndex(swipeableState.targetValue))
            }
        }
    }

    LaunchedEffect(word) {
        swipeableState.snapTo(WordSwipeDirection.CENTER.index)
    }

    AutoSizedText(
        text = word,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
            .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
            .alpha(getAlphaFromSwipeProgress(swipeableState.progress))
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Vertical
            )
    )

}

enum class WordSwipeDirection(val index: Int) {
    UP(0),
    CENTER(1),
    DOWN(2);

    companion object {
        fun fromIndex(index: Int) = values().find { it.index == index } ?: error("Unknown swipe index")
    }
}

private fun <T> getAlphaFromSwipeProgress(progress: SwipeProgress<T>): Float {
    if (progress.to == WordSwipeDirection.CENTER.index) return 1f

    return 1 - progress.fraction
}
