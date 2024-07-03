package com.duwna.debelias.presentation.screens.game_playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.duwna.debelias.presentation.composables.PointsCounter
import com.duwna.debelias.presentation.screens.game_playing.composables.SwipeableWord
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GamePlayingScreen(
    viewModel: GamaPlayingViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    var containerHeight by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .onGloballyPositioned { containerHeight = it.size.height }
            .testTag("box_tag")
    ) {
        val currentState = state

        if (currentState != null) {

            PointsCounter(
                value = currentState.currentPoints,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .height(40.dp)
                    .widthIn(40.dp)
                    .align(Alignment.TopCenter)
            )

            SwipeableWord(
                word = currentState.currentWord,
                onSwiped = viewModel::onWordSwiped,
                containerHeightPx = containerHeight / density.density,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
                    .testTag("word_tag")
            )

            Text(
                text = currentState.secondsString,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
            )

        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}
