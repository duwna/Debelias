package com.duwna.debelias.presentation.screens.settings

import com.duwna.debelias.domain.models.GameGroup

data class SettingsViewState(
    val groups: List<GameGroup> = emptyList(),
) {

}
