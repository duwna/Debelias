package com.duwna.debelias.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.duwna.debelias.R
import com.duwna.debelias.presentation.screens.settings.composable.SliderWithText

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {


//        SliderWithText(
//            selectedText = stringResource(id = R.string.settings_duration_title, state.settings.maxPoints),
//            selectedValue = state.durationSliderValue,
//            onValueChanged = viewModel::onDurationChanged,
//            onValueChangeFinished = viewModel::saveDuration
//        )
//
//        SliderWithText(
//            selectedText = stringResource(id = R.string.settings_rotation_title, state.settings.maxSeconds),
//            selectedValue = state.rotationsCountSliderValue,
//            onValueChanged = viewModel::onRotationsCountChanged,
//            onValueChangeFinished = viewModel::saveRotationsCount
//        )
    }
}
