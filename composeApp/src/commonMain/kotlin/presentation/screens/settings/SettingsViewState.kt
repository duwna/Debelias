package com.duwna.debelias.presentation.screens.settings

import com.duwna.debelias.domain.models.GameGroup
import com.duwna.debelias.domain.models.Settings
import com.duwna.debelias.domain.models.Settings.Companion.failureWordPoints
import com.duwna.debelias.domain.models.Settings.Companion.maxPoints
import com.duwna.debelias.domain.models.Settings.Companion.roundSeconds
import com.duwna.debelias.domain.models.Settings.Companion.successWordPoints
import com.duwna.debelias.presentation.utils.SliderFractionUtils

data class SettingsViewState(
    val groups: List<GameGroup>,
    val settings: Settings
) {

    val canRemoveGroup: Boolean
        get() = groups.size > 2

    val maxPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(maxPoints, settings.maxPoints)

    val roundSecondsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(roundSeconds, settings.roundSeconds)

    val successWordPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(successWordPoints, settings.successWordPoints)

    val failureWordPointsSliderValue: Float
        get() = SliderFractionUtils.getSliderFraction(failureWordPoints, settings.failureWordPoints)

}
