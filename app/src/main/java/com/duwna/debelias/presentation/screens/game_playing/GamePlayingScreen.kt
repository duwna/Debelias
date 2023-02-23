package com.duwna.debelias.presentation.screens.game_playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GamePlayingScreen(viewModel: GamaPlayingViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        state.currentWord?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }

        Button(onClick = { viewModel.showNewWord() }) {
            Text(text = "Show new")
        }
    }
}
