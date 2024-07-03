package com.duwna.debelias.presentation.screens.game_playing.composables

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableWord(
    modifier: Modifier = Modifier,
    word: String,
    containerHeightPx: Float,
    onSwiped: (WordSwipeDirection) -> Unit
) {

    // TODO fix decayAnimationSpec
    val anchoredDraggableState = remember(containerHeightPx) {
        AnchoredDraggableState<WordSwipeDirection>(
            initialValue = WordSwipeDirection.CENTER,
            anchors = DraggableAnchors {
                WordSwipeDirection.UP at -containerHeightPx
                WordSwipeDirection.CENTER at 0f
                WordSwipeDirection.DOWN at containerHeightPx
            },
            positionalThreshold = { it },
            velocityThreshold = { 0f },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay(),
            confirmValueChange = { it != WordSwipeDirection.CENTER }
        )
    }

    if (anchoredDraggableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                onSwiped.invoke(anchoredDraggableState.targetValue)
            }
        }
    }

    LaunchedEffect(word) {
        anchoredDraggableState.snapTo(WordSwipeDirection.CENTER)
    }

    val progress = (1 - abs(anchoredDraggableState.offset / containerHeightPx))
        .coerceAtLeast(0.1f)

    AutoSizedText(
        text = word,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
            .offset { IntOffset(0, anchoredDraggableState.offset.roundToInt()) }
            .scale(progress)
            .alpha(progress)
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Vertical
            )
    )

}

enum class WordSwipeDirection {
    UP,
    CENTER,
    DOWN;
}
