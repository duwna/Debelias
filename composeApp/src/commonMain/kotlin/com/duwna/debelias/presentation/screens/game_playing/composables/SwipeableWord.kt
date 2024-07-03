package com.duwna.debelias.presentation.screens.game_playing.composables

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableWord(
    modifier: Modifier = Modifier,
    word: String,
    onSwiped: (WordSwipeDirection) -> Unit
) {
//    val maxSwipeOffset = LocalWindowInfo.current.containerSize.height.toFloat()
    val maxSwipeOffset = 200.dp.value

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = WordSwipeDirection.CENTER,
            anchors = DraggableAnchors {
                WordSwipeDirection.UP at -maxSwipeOffset
                WordSwipeDirection.CENTER at 0f
                WordSwipeDirection.DOWN at maxSwipeOffset
            },
            positionalThreshold = {
                it
            },
            velocityThreshold = {
                1f
            },
            animationSpec = tween(),
            confirmValueChange = { true }
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

    AutoSizedText(
        text = word,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
            .offset { IntOffset(0, anchoredDraggableState.offset.roundToInt()) }
            .alpha(getAlphaFromSwipeProgress(anchoredDraggableState))
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

@OptIn(ExperimentalFoundationApi::class)
private fun getAlphaFromSwipeProgress(state: AnchoredDraggableState<WordSwipeDirection>): Float {
    return state.progress
//    if (progress.to == WordSwipeDirection.CENTER.index) return 1f
//
//    return 1 - progress.fraction
}


//@Composable
//fun SwipeableWord(
//    modifier: Modifier = Modifier,
//    word: String,
//    onSwiped: (WordSwipeDirection) -> Unit
//) {
//    AutoSizedText(
//        text = word,
//        color = MaterialTheme.colorScheme.onBackground,
//        textAlign = TextAlign.Center,
//        style = MaterialTheme.typography.displayLarge,
//        modifier = modifier
//    )
//}
