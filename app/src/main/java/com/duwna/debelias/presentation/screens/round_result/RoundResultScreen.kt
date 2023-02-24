package com.duwna.debelias.presentation.screens.round_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duwna.debelias.R
import com.duwna.debelias.presentation.composables.PointsCounter
import com.duwna.debelias.presentation.screens.round_result.composables.PlayingGroupCard
import com.duwna.debelias.presentation.screens.round_result.composables.TopCard

@Composable
fun RoundResultScreen(viewModel: RoundResultViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val currentState = state

        if (currentState != null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                TopCard(
                    maxPoints = currentState.maxPoints,
                    onExitClicked = viewModel::onExitClicked
                )

                Spacer(modifier = Modifier.height(40.dp))

                currentState
                    .playingGroups
                    .forEachIndexed { index, group ->
                        PlayingGroupCard(
                            playingGroup = group,
                            isSelected = currentState.isGroupSelected(index)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                Spacer(modifier = Modifier.weight(1f))

                if (currentState.addedPoints != null) {
                    PointsCounter(
                        value = currentState.addedPoints,
                        showSign = true,
                        modifier = Modifier
                            .size(40.dp)
                            .align(CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = viewModel::continueGame) {
                    Text(
                        text = stringResource(
                            when (currentState.addedPoints) {
                                null -> R.string.start_game
                                else -> R.string.continue_game
                            }
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = currentState.nextGroupName)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
