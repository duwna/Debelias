package com.duwna.debelias.presentation.screens.game_playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duwna.debelias.presentation.composables.PointsCounter
import com.duwna.debelias.presentation.screens.game_playing.composables.SwipeableWord

@Composable
fun GamePlayingScreen(viewModel: GamaPlayingViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)
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
