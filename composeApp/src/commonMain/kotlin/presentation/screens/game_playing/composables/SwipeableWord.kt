package presentation.screens.game_playing.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun SwipeableWord(
//    modifier: Modifier = Modifier,
//    word: String,
//    onSwiped: (WordSwipeDirection) -> Unit
//) {
//    val swipeableState = rememberSwipeableState(WordSwipeDirection.CENTER.index)
//    val maxSwipeOffset = LocalConfiguration.current.screenHeightDp.toFloat()
//
//    val anchoredDraggableState = remember {
//        AnchoredDraggableState<WordSwipeDirection>(
//            initialValue = WordSwipeDirection.CENTER,
//            anchors = DraggableAnchors {
//                WordSwipeDirection.UP at -maxSwipeOffset
//                WordSwipeDirection.CENTER at 0f
//                WordSwipeDirection.DOWN at maxSwipeOffset
//            },
//        )
//    }
//
//    if (swipeableState.isAnimationRunning) {
//        DisposableEffect(Unit) {
//            onDispose {
//                onSwiped.invoke(WordSwipeDirection.fromIndex(swipeableState.targetValue))
//            }
//        }
//    }
//
//    LaunchedEffect(word) {
//        swipeableState.snapTo(WordSwipeDirection.CENTER.index)
//    }
//
//    AutoSizedText(
//        text = word,
//        color = MaterialTheme.colorScheme.onBackground,
//        textAlign = TextAlign.Center,
//        style = MaterialTheme.typography.displayLarge,
//        modifier = modifier
//            .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
//            .alpha(getAlphaFromSwipeProgress(swipeableState.progress))
//            .anchoredDraggable(
//                state = anchoredDraggableState,
//                orientation = Orientation.Vertical
//            )
////            .swipeable(
////                state = swipeableState,
////                anchors = anchors,
////                orientation = Orientation.Vertical
////            )
//    )
//
//}

enum class WordSwipeDirection(val index: Int) {
    UP(0),
    CENTER(1),
    DOWN(2);

    companion object {
        fun fromIndex(index: Int) =
            entries.find { it.index == index } ?: error("Unknown swipe index")
    }
}

//private fun <T> getAlphaFromSwipeProgress(progress: SwipeProgress<T>): Float {
//    if (progress.to == WordSwipeDirection.CENTER.index) return 1f
//
//    return 1 - progress.fraction
//}


@Composable
fun SwipeableWord(
    modifier: Modifier = Modifier,
    word: String,
    onSwiped: (WordSwipeDirection) -> Unit
) {
    AutoSizedText(
        text = word,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
    )
}
