package com.duwna.debelias.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duwna.debelias.R
import com.duwna.debelias.presentation.screens.settings.composable.GroupInput
import com.duwna.debelias.presentation.screens.settings.composable.SliderWithText

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                currentState.groups.forEachIndexed { index, group ->
                    GroupInput(
                        name = group.name,
                        canRemoveGroup = currentState.canRemoveGroup,
                        modifier = Modifier.fillMaxWidth(),
                        onTextChanged = { viewModel.onGroupNameChanged(it, index) },
                        onRemoveClicked = { viewModel.onRemoveGroupClicked(index) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = viewModel::addGroup,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.add_button))
                }

                Spacer(modifier = Modifier.height(30.dp))

                SliderWithText(
                    selectedText = stringResource(R.string.slider_round_seconds, currentState.settings.roundSeconds),
                    selectedValue = currentState.roundSecondsSliderValue,
                    onValueChanged = viewModel::setRoundSeconds,
                    onValueChangeFinished = viewModel::saveRoundSeconds
                )

                SliderWithText(
                    selectedText = stringResource(R.string.slider_max_points, currentState.settings.maxPoints),
                    selectedValue = currentState.maxPointsSliderValue,
                    onValueChanged = viewModel::setMaxPoints,
                    onValueChangeFinished = viewModel::saveMaxPoints
                )

                SliderWithText(
                    selectedText = stringResource(R.string.slider_success_word_points, currentState.settings.successWordPoints),
                    selectedValue = currentState.successWordPointsSliderValue,
                    onValueChanged = viewModel::setSuccessWordPoints,
                    onValueChangeFinished = viewModel::saveSuccessWordPoints
                )

                SliderWithText(
                    selectedText = stringResource(R.string.slider_failure_word_points, currentState.settings.failureWordPoints),
                    selectedValue = currentState.failureWordPointsSliderValue,
                    onValueChanged = viewModel::setFailureWordPoints,
                    onValueChangeFinished = viewModel::saveFailureWordPoints
                )
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
